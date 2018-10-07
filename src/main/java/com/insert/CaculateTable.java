package com.insert;

import com.entity.Constants;
import com.file.GetExcelValue;
import com.util.Util;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import sun.nio.cs.UTF_32LE;
import sun.security.krb5.internal.crypto.HmacMd5ArcFourCksumType;

import java.awt.*;
import java.util.List;
import java.util.Map;

/**
 * 计算最后几个表格
 * 时间 : 2018/10/6.
 */
public class CaculateTable {

    public static void caculateTable1(XWPFTable table, Map<String, Object> caculateParams) {

        //1.从Map里获取计算参数
        //混泥土等级
        String concreteGrade = caculateParams.get(Constants.CONCRETE_GRADE).toString();
        String concreteGradeFc = caculateParams.get(Constants.CONCRETE_GRADE_FC).toString();
        String concreteGradeFck = caculateParams.get(Constants.CONCRETE_GRADE_FCK).toString();
        String concreteGradeFt = caculateParams.get(Constants.CONCRETE_GRADE_FT).toString();
        String concreteGradeFtk = caculateParams.get(Constants.CONCRETE_GRADE_FTK).toString();

        //钢筋等级
        String steelGrade = caculateParams.get(Constants.STEEL_GRADE).toString();
        String steelGradeFstk = caculateParams.get(Constants.STEEL_GRADE_FSTK).toString();
        String steelGradeFyk = caculateParams.get(Constants.STEEL_GRADE_FYK).toString();
        String steelGradeFyvk = caculateParams.get(Constants.STEEL_GRADE_FYVK).toString();

        //截面属性(mm)
        String sectionB = caculateParams.get(Constants.SECTION_B).toString();
        String sectionH = caculateParams.get(Constants.SECTION_H).toString();

        //受力状况(kN,KN*m)
        String stressConditionV = caculateParams.get(Constants.STRESS_CONDITION_V).toString();
        String stressConditionM = caculateParams.get(Constants.STRESS_CONDITION_M).toString();

        //箍筋(mm)
        String hoopD = caculateParams.get(Constants.HOOP_D).toString();
        String hoopL = caculateParams.get(Constants.HOOP_L).toString();


        String _afS = caculateParams.get(Constants.PARAM_afS).toString();
        String _af1 = caculateParams.get(Constants.PARAM_af1).toString();
        Double _yre = 0.85;
        Double _afCV = 0.7;

        System.out.println("=============== 计算参数 ===================");
        System.out.println("混泥土等级：" + concreteGrade);
        System.out.println("混泥土等级参数：\nFc= " + concreteGradeFc + "\nFck= " + concreteGradeFck + "\nFt= " + concreteGradeFt + "\nFtk= " + concreteGradeFtk);
        System.out.println("钢筋等级：" + steelGrade);
        System.out.println("钢筋等级：\nFstk= " + steelGradeFstk + "\nFyk= " + steelGradeFyk + "\nFyvk= " + steelGradeFyvk);
        System.out.println("截面属性：\nb= " + sectionB + "\nh= " + sectionH);
        System.out.println("受力状况：\nv= " + stressConditionV + "\nM= " + stressConditionM);
        System.out.println("箍筋：\nd= " + hoopD + "\nl= " + hoopL);
        System.out.println("剩余参数：\n_afS= " + _afS + "\n_af1= " + _af1 + "\n_yre= " + _yre + "\n_afCV= " + _afCV);

        //3.根据公式进行计算
        //1).受弯验算
        Double h0 = Double.valueOf(sectionH) - Double.valueOf(_afS);
        Double _afs_ = Double.valueOf(stressConditionM) / (Double.valueOf(_af1) * Double.valueOf(concreteGradeFck) * Double.valueOf(sectionB) * h0 * h0);
        Double _ypclong_ = 1.00 - Math.pow(Double.valueOf(1 - 2 * _afs_),0.5);
        Double _As_ = _ypclong_ * Double.valueOf(sectionB) * h0 * Double.valueOf(_af1) * Double.valueOf(concreteGradeFck) / Double.valueOf(steelGradeFstk);
        Double _Ps_ = _As_ / (Double.valueOf(sectionB) * h0);

        //1).受剪验算
        Double _shearForce1_ = 0.2 * Double.valueOf(concreteGradeFck) * Double.valueOf(sectionB) * h0 / _yre;
        Double _shearForce2_ = ((0.6 * _afCV * Double.valueOf(concreteGradeFtk) * Double.valueOf(sectionB) * h0)+
                ((Double.valueOf(steelGradeFyvk) * Double.valueOf(sectionH) * 0.25 * Math.PI * Math.pow(Double.valueOf(hoopD),2) * h0) / 8)) /_yre;
        System.out.println("=============== 计算结果 ===================");
        System.out.println("受弯验算 ：\n_afs_  = "+ _afs_ + "\n_ypclong_ = " + _ypclong_ + "\n_As_ = "+ _As_ + "\n_Ps_ = "+_Ps_ );
        System.out.println("受剪验算 ：\n_shearForce1_  = "+ _shearForce1_ + "\n_shearForce1_ = " + _shearForce1_ );

        //4.将计算的结果回填到表格中
        Util.insertValueToCell(table.getRow(0).getCell(2),sectionB);
        Util.insertValueToCell(table.getRow(0).getCell(5),sectionH);
        Util.insertValueToCell(table.getRow(1).getCell(2),stressConditionV);
        Util.insertValueToCell(table.getRow(1).getCell(5),stressConditionM);
        Util.insertValueToCell(table.getRow(2).getCell(2),concreteGrade);
        Util.insertValueToCell(table.getRow(3).getCell(2),concreteGradeFck);
        Util.insertValueToCell(table.getRow(3).getCell(5),_afS);
        Util.insertValueToCell(table.getRow(4).getCell(2),concreteGradeFc);
        Util.insertValueToCell(table.getRow(4).getCell(5),concreteGradeFtk);
        Util.insertValueToCell(table.getRow(5).getCell(2),concreteGrade);
        Util.insertValueToCell(table.getRow(6).getCell(2),steelGradeFstk);
        Util.insertValueToCell(table.getRow(6).getCell(5),steelGradeFyvk);
        Util.insertValueToCell(table.getRow(7).getCell(2),hoopD);
        Util.insertValueToCell(table.getRow(7).getCell(5),hoopL);
        Util.insertValueToCell(table.getRow(8).getCell(2),_afs_.toString());
        Util.insertValueToCell(table.getRow(8).getCell(4),_ypclong_.toString());
        Util.insertValueToCell(table.getRow(9).getCell(2),_As_.toString());
        Util.insertValueToCell(table.getRow(9).getCell(5),_Ps_.toString());
        Util.insertValueToCell(table.getRow(10).getCell(2),_shearForce1_.toString());
        Util.insertValueToCell(table.getRow(11).getCell(2),_shearForce1_.toString());
    }

    public static void caculateTable2(XWPFTable table, Map<String, Object> caculateParams) {

    }

    public static void caculateTable3(XWPFTable table, Map<String, Object> caculateParams) {

    }
}
