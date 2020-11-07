package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetMealService;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.jxls.transform.poi.PoiContext;
import org.jxls.util.JxlsHelper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 统计报表
 */
@RestController
@RequestMapping("report")
public class ReportController {

    @Reference
    private MemberService memberService;

    @Reference
    private SetMealService setmealService;

    @Reference
    private ReportService reportService;

    /**
     * 会员折线图
     *
     * @return
     */
    @GetMapping("getMemberReport")
    public Result getMemberReport() {
        // 组装过去12个月的数据, 前端是一个数组
        List<String> months = new ArrayList<>();
        // 使用java中的calendar来操作日期, 日历对象
        Calendar calendar = Calendar.getInstance();
        // 过去一年, 年-1
        calendar.add(Calendar.YEAR, -1);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        // 遍历12次，依次加1个月
        for (int i = 0; i < 12; i++) {
            // +1个月
            calendar.add(Calendar.MONTH, 1);
            months.add(sdf.format(calendar.getTime()));
        }
        // 调用服务去查询12个月的数据
        List<Integer> memberCount = memberService.getMemberReport(months);
        // 构建返回的数据
        Map<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    //根据传入的月份查询数据
    @GetMapping("getMemberReportBetweenDays")
    public Result getMemberReportBetweenDays(String start,String end) throws Exception {
        //创建数组，封装两个时间段之间的月份
        List<String> months = new ArrayList<>();
        //根据字符串创建对应的日历对象
        Calendar calendarStart = Calendar.getInstance();
        Calendar calendarEnd = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        //设置日历
        calendarStart.setTime(sdf.parse(start));
        calendarEnd.setTime(sdf.parse(end));
        //判断截止时间是否大于起始时间
        while (calendarEnd.getTimeInMillis() >= calendarStart.getTimeInMillis()) {
            months.add(sdf.format(calendarStart.getTime()));
            calendarStart.add(Calendar.MONTH, 1);
        }
        List<Integer> memberCount = memberService.getMemberReport(months);
        // 构建返回的数据
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("months", months);
        paramMap.put("memberCount", memberCount);
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, paramMap);
    }

    /**
     * @return
     */
    @GetMapping("getSetmealReport")
    public Result getSetmealReport() {
        // 调用服务查询
        // 套餐数量
        List<Map<String, Object>> setmealCount = setmealService.findSetmealCount();
        // 套餐名称集合
        List<String> setmealNames = new ArrayList<>();
        // [{name:,value}]
        // 抽取套餐名称
        if (setmealCount != null) {
            setmealCount.forEach(s -> setmealNames.add((String) s.get("name")));
        }
        // 封装返回的结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("setmealNames", setmealNames);
        resultMap.put("setmealCount", setmealCount);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, resultMap);
    }

    /**
     * 运营统计数据
     *
     * @return
     */
    @GetMapping("getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> businessReport = reportService.getBusinessReport();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, businessReport);
    }

    /**
     * 导出运营统计数据报Excel表
     *
     * @param req
     * @param res
     * @throws Exception
     */
    @GetMapping("exportBusinessReportExcel")
    public void exportBusinessReportExcel(HttpServletRequest req, HttpServletResponse res) throws Exception {
        //- 获取模板所在
        String templatePath = req.getSession().getServletContext().getRealPath("/template/report_template.xlsx");
        //- 获取报表数据
        Map<String, Object> businessReport = reportService.getBusinessReport();
        //- 构建要填充的数据
        PoiContext context = new PoiContext();
        context.putVar("obj", businessReport);

        //- 设置响应体内容的格式application/vnd.ms-excel
        res.setContentType("application/vnd.ms-excel");
        String filename = "运营数据统计.xlsx";
        byte[] bytes = filename.getBytes();
        filename = new String(bytes, "ISO-8859-1");
        //- 设置响应头信息，告诉浏览器下载的文件名叫什么 Content-Disposition, attachment;filename=文件名
        res.setHeader("Content-Disposition", "attachment;filename=" + filename);

        // 把数据模型中的数据填充到文件中
        JxlsHelper.getInstance().processTemplate(new FileInputStream(templatePath), res.getOutputStream(), context);
    }

    /**
     * 导出运营统计数据报表PDF格式
     *
     * @param req
     * @param res
     * @throws Exception
     */
    @GetMapping("exportBusinessReportPDF")
    public void exportBusinessReportPDF(HttpServletRequest req, HttpServletResponse res) throws Exception {
        // 获取模板的路径, getRealPath("/") 相当于到webapp目录下
        String basePath = req.getSession().getServletContext().getRealPath("/template");
        // jrxml路径
        String jrxml = basePath + File.separator + "health_business.jrxml";
        // jasper路径
        String jasper = basePath + File.separator + "report_business.jasper";

        // 编译模板
        JasperCompileManager.compileReportToFile(jrxml, jasper);
        Map<String, Object> businessReport = reportService.getBusinessReport();
        // 热门套餐(list -> Detail1)
        List<Map<String, Object>> hotSetmeals = (List<Map<String, Object>>) businessReport.get("hotSetmeal");
        // 填充数据 JRBeanCollectionDataSource自定义数据
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, businessReport, new JRBeanCollectionDataSource(hotSetmeals));
        res.setContentType("application/pdf");
        res.setHeader("Content-Disposition", "attachement;filename=businessReport.pdf");
        JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());

    }
}
