package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.constant.MessageConstant;
import com.itheima.health.entity.Result;
import com.itheima.health.service.MemberService;
import com.itheima.health.service.ReportService;
import com.itheima.health.service.SetmealService;
import net.sf.jasperreports.engine.*;
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
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        //组装过去12个月的数据 2020-01
        List<String> months = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        //过去一年，年-1
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        calendar.add(Calendar.YEAR, -1);
        for (int i = 0; i < 12; i++) {
            calendar.add(Calendar.MONTH, 1);
            months.add(sdf.format(calendar.getTime()));
        }
        //调用业务层查询12个月的数据
        List<Integer> memberCount = memberService.getMemberReport(months);
        HashMap<String, Object> map = new HashMap<>();
        map.put("months", months);
        map.put("memberCount", memberCount);
        //响应结果
        return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
    }

    //套餐预约占比
    @GetMapping("/getSetmealReport")
    public Result getSetmealReport() {
        // 调用服务查询
        // 套餐数量
        List<Map<String, Object>> setmealCount = setmealService.findSetmealCount();
        List<String> setmealNames = new ArrayList<>();
        if (setmealCount != null) {
            setmealCount.forEach(s -> setmealNames.add((String) s.get("name")));
        }
        //返回的结果
        Map<String, Object> map = new HashMap<>();
        map.put("setmealNames", setmealNames);
        map.put("setmealCount", setmealCount);
        return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);


    }

    //运营统计数据
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> businessReport = reportService.getBusinessReport();
        return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, businessReport);
    }

    //导出运营数据统计报表 pdf格式
    @GetMapping("/exportBusinessReportPdf")
    public Result exportBusinessReportPdf(HttpServletRequest req, HttpServletResponse res) {
        //获取模板的路径
        String basePath = req.getSession().getServletContext().getRealPath("/template");
        //jrxml路径
        String jrxml = basePath + File.separator + "health_business3.jrxml";
        //jasper路径
        String jasper = basePath + File.separator + "report_business.jasper";

        //编译模板
        try {
            JasperCompileManager.compileReportToFile(jrxml, jasper);
            //从数据库获取数据
            Map<String, Object> businessReport = reportService.getBusinessReport();
            //热门套餐
            List<Map<String, Object>> hotSetmeals = (List<Map<String, Object>>) businessReport.get("hotSetmeal");
            // 填充数据 JRBeanCollectionDataSource自定义数据
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasper, businessReport, new JRBeanCollectionDataSource(hotSetmeals));

            res.setContentType("application/pdf");
            res.setHeader("Content-Disposition", "attachement;filename=businessReport.pdf");
            JasperExportManager.exportReportToPdfStream(jasperPrint, res.getOutputStream());
            return null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Result(false, "导出运营数据统计pdf失败");


    }

    //到处运营数据统计报表 excel
    @GetMapping("/exportBusinessReport")
    public Result exportBusinessReport(HttpServletRequest req,HttpServletResponse res) throws Exception {
        //获取模板路径
        String template = req.getSession().getServletContext().getRealPath("/template/report_template2.xlsx");
        //从数据库获取数据
        Map<String, Object> businessReport = reportService.getBusinessReport();
        //构建填充的数据
        PoiContext context = new PoiContext();
        //放入数据
        context.putVar("obj",businessReport);
        // 工作簿写给reponse输出流
        res.setContentType("application/vnd.ms-excel");
        String filename = "运营统计数据报表.xlsx";
        // 解决下载的文件名 中文乱码
        filename = new String(filename.getBytes(), "ISO-8859-1");
        // 设置头信息，告诉浏览器，是带附件的，文件下载
        res.setHeader("Content-Disposition","attachement;filename=" + filename);
        JxlsHelper.getInstance().processTemplate(new FileInputStream(template),res.getOutputStream(),context);
        return null;
    }
}




































