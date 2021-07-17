package com.insert;

import com.data.Cantilever_3;
import com.data.Girder_0;
import com.data.MaterialProperty_1;
import com.data.Pillar_2;
import com.util.Util;
import org.apache.poi.xwpf.usermodel.XWPFTable;

/**
 * 计算最后几个表格
 * 时间 : 2018/10/6.
 */
public class CaculateTable {

    /**
     * 子结构框架梁 受弯受剪 验算
     *
     * @param table
     */
    public static void caculateTable1(XWPFTable table) {

        //混泥土等级
        String concreteGrade = Girder_0.CONCRETE_GRADE;
        MaterialProperty_1.ConcreteGrade concreteGrade1 = MaterialProperty_1.CONCRETE_GRADE_MAP.get(concreteGrade);

        //钢筋等级
        String steelGrade = Girder_0.STEEL_GRADE;
        MaterialProperty_1.SteelGrade steelGrade1 = MaterialProperty_1.STEEL_GRADE_MAP.get(steelGrade);


        Double _yre = 0.85;
        Double _afCV = 0.7;

        //3.根据公式进行计算
        //1).受弯验算
        Double h0 = Double.valueOf(Girder_0.SECTION_H) - Girder_0.PARAM_afS;
        Double _afs_ = Double.valueOf(Girder_0.STRESS_CONDITION_M) * 1000000 / (Girder_0.PARAM_af1 * Double.valueOf(concreteGrade1.fck) * Double.valueOf(Girder_0.SECTION_B) * h0 * h0);
        Double _ypclong_ = 1.00 - Math.pow(Double.valueOf(1 - (2 * _afs_)), 0.5);
        Double _As_ = _ypclong_ * Double.valueOf(Girder_0.SECTION_B) * h0 * Girder_0.PARAM_af1 * Double.valueOf(concreteGrade1.fck) / Double.valueOf(steelGrade1.fstk);
        Double _Ps_ = _As_ / (Double.valueOf(Girder_0.SECTION_B) * h0);

        //1).受剪验算
        Double _shearForce1_ = 0.2 * Double.valueOf(concreteGrade1.fck) * Double.valueOf(Girder_0.SECTION_B) * h0 / (_yre * 1000);
        Double _shearForce2_ = ((0.6 * _afCV * Double.valueOf(concreteGrade1.ftk) * Double.valueOf(Girder_0.SECTION_B) * h0) +
                ((Double.valueOf(steelGrade1.fyvk) * 4 * 0.25 * Math.PI * Math.pow(Double.valueOf(Girder_0.HOOP_D), 2) * h0) / Double.valueOf(Girder_0.HOOP_L))) / (_yre * 1000);
        System.out.println("=============== 计算结果 ===================");
        System.out.println("受弯验算 ：\n_afs_  = " + _afs_ + "\n_ypclong_ = " + _ypclong_ + "\n_As_ = " + _As_ + "\n_Ps_ = " + _Ps_);
        System.out.println("受剪验算 ：\n_shearForce1_  = " + _shearForce1_ + "\n_shearForce2_ = " + _shearForce2_);

        //4.比较数据，得出结论
        String _Ps1_ = table.getRow(9).getCell(6).getText();
        Double _Ps11_ = Double.valueOf(_Ps1_.substring(1, _Ps1_.length() - 1));
        String result;
        if (_Ps_ < _Ps11_ && _shearForce1_ > Double.valueOf(Girder_0.STRESS_CONDITION_V) && _shearForce2_ > Double.valueOf(Girder_0.STRESS_CONDITION_V)) {
            //通过
            result = "通过";
        } else {
            result = "不通过";
        }

        //5.将计算的结果回填到表格中
        Util.insertValueToCell(table.getRow(0).getCell(2), Util.getPrecisionString(Girder_0.SECTION_B, 1));
        Util.insertValueToCell(table.getRow(0).getCell(5), Util.getPrecisionString(Girder_0.SECTION_H, 1));
        Util.insertValueToCell(table.getRow(1).getCell(2), Util.getPrecisionString(Girder_0.STRESS_CONDITION_V, 1));
        Util.insertValueToCell(table.getRow(1).getCell(5), Util.getPrecisionString(Girder_0.STRESS_CONDITION_M, 1));
        Util.insertValueToCell(table.getRow(2).getCell(2), concreteGrade);
        Util.insertValueToCell(table.getRow(3).getCell(2), Util.getPrecisionString(concreteGrade1.fck, 2));
        Util.insertValueToCell(table.getRow(3).getCell(5), Util.getPrecisionString(Girder_0.PARAM_afS, 2));
        Util.insertValueToCell(table.getRow(4).getCell(2), Util.getPrecisionString(concreteGrade1.fc, 2));
        Util.insertValueToCell(table.getRow(4).getCell(5), Util.getPrecisionString(concreteGrade1.ftk, 2));
        Util.insertValueToCell(table.getRow(5).getCell(2), steelGrade);
        Util.insertValueToCell(table.getRow(6).getCell(2), Util.getPrecisionString(steelGrade1.fstk, 0));
        Util.insertValueToCell(table.getRow(6).getCell(5), Util.getPrecisionString(steelGrade1.fyvk, 0));
        Util.insertValueToCell(table.getRow(7).getCell(2), Util.getPrecisionString(Girder_0.HOOP_D, 0));
        Util.insertValueToCell(table.getRow(7).getCell(5), Util.getPrecisionString(Girder_0.HOOP_L, 0));
        Util.insertValueToCell(table.getRow(8).getCell(2), Util.getPrecisionString(_afs_, 3));
        Util.insertValueToCell(table.getRow(8).getCell(4), Util.getPrecisionString(_ypclong_, 3));
        Util.insertValueToCell(table.getRow(9).getCell(2), Util.getPrecisionString(_As_, 0));
        Util.insertValueToCell(table.getRow(9).getCell(5), Util.getPrecisionString(_Ps_ * 100, 2) + "%");
        Util.insertValueToCell(table.getRow(10).getCell(2), Util.getPrecisionString(_shearForce1_, 0));
        Util.insertValueToCell(table.getRow(10).getCell(4), result);
        Util.insertValueToCell(table.getRow(11).getCell(2), Util.getPrecisionString(_shearForce2_, 0));
    }

    /**
     * 子结构框架柱抗剪验算
     *
     * @param table
     */
    public static void caculateTable2(XWPFTable table) {
        //混泥土等级
        String concreteGrade = Pillar_2.CONCRETE_GRADE;
        MaterialProperty_1.ConcreteGrade concreteGrade1 = MaterialProperty_1.CONCRETE_GRADE_MAP.get(concreteGrade);

        //钢筋等级
        String steelGrade = Pillar_2.STEEL_GRADE;
        MaterialProperty_1.SteelGrade steelGrade1 = MaterialProperty_1.STEEL_GRADE_MAP.get(steelGrade);

        Double _yre = 0.85;

        //3.根据公式进行计算
        Double h0 = Double.valueOf(Pillar_2.SECTION_H) - Pillar_2.PARAM_afS;
        Double _result1_ = 0.2 * concreteGrade1.fck * Double.valueOf(Pillar_2.SECTION_B) * h0 / (_yre * 1000);
        Double _lamda_ = (Double.valueOf(Pillar_2.FLOOR_H) - Pillar_2.PARAM_afS) / (2 * h0);
        _lamda_ = _lamda_ <= 3 ? _lamda_ : 3.00d;
        Double _result3_ = (((1.05 * concreteGrade1.ftk * Double.valueOf(Pillar_2.SECTION_B) * h0) / (_lamda_ + 1.00)) +
                (Double.valueOf(steelGrade1.fyvk) * 4 * 0.25 * Math.PI * Math.pow(Double.valueOf(Pillar_2.HOOP_D), 2) * h0 / Double.valueOf(Pillar_2.HOOP_L)) +
                (0.056 * Double.valueOf(Pillar_2.STRESS_CONDITION_P) * 1000)) / (_yre * 1000);
        System.out.println("=============== 计算结果 ===================");
        System.out.println("受剪验算 ：\n_result1_  = " + _result1_ + "\n_lamda_ = " + _lamda_ + "\n_result3_ =  " + _result3_);


        //4.比较数据，得出结论
        String result;
        if (_result1_ > Double.valueOf(Pillar_2.STRESS_CONDITION_V) && _result3_ > Double.valueOf(Pillar_2.STRESS_CONDITION_V)) {
            //通过
            result = "通过";
        } else {
            result = "不通过";
        }

        //5.将计算的结果回填到表格中
        Util.insertValueToCell(table.getRow(0).getCell(2), Util.getPrecisionString(Pillar_2.SECTION_B, 1));
        Util.insertValueToCell(table.getRow(0).getCell(5), Util.getPrecisionString(Pillar_2.SECTION_H, 1));
        Util.insertValueToCell(table.getRow(1).getCell(1), Util.getPrecisionString(Pillar_2.FLOOR_H, 0));
        Util.insertValueToCell(table.getRow(2).getCell(2), Util.getPrecisionString(Pillar_2.STRESS_CONDITION_V, 1));
        Util.insertValueToCell(table.getRow(2).getCell(5), Util.getPrecisionString(Pillar_2.STRESS_CONDITION_M, 1));
        Util.insertValueToCell(table.getRow(3).getCell(2), Util.getPrecisionString(Pillar_2.STRESS_CONDITION_P, 1));
        Util.insertValueToCell(table.getRow(4).getCell(2), concreteGrade);
        Util.insertValueToCell(table.getRow(5).getCell(2), Util.getPrecisionString(concreteGrade1.fck, 2));
        Util.insertValueToCell(table.getRow(5).getCell(5), Util.getPrecisionString(Pillar_2.PARAM_afS, 2));
        Util.insertValueToCell(table.getRow(6).getCell(2), Util.getPrecisionString(concreteGrade1.fc, 2));
        Util.insertValueToCell(table.getRow(6).getCell(5), Util.getPrecisionString(concreteGrade1.ftk, 2));
        Util.insertValueToCell(table.getRow(7).getCell(2), steelGrade);
        Util.insertValueToCell(table.getRow(8).getCell(2), Util.getPrecisionString(steelGrade1.fstk, 0));
        Util.insertValueToCell(table.getRow(8).getCell(5), Util.getPrecisionString(steelGrade1.fyvk, 0));
        Util.insertValueToCell(table.getRow(9).getCell(2), Util.getPrecisionString(Pillar_2.HOOP_D, 0));
        Util.insertValueToCell(table.getRow(9).getCell(5), Util.getPrecisionString(Pillar_2.HOOP_L, 0));

        Util.insertValueToCell(table.getRow(10).getCell(2), Util.getPrecisionString(_result1_, 0));
        Util.insertValueToCell(table.getRow(10).getCell(4), result);
        Util.insertValueToCell(table.getRow(11).getCell(2), Util.getPrecisionString(_lamda_, 2));
        Util.insertValueToCell(table.getRow(12).getCell(2), Util.getPrecisionString(_result3_, 0));
    }

    /**
     * 悬臂墙配筋验算
     *
     * @param table
     */
    public static void caculateTable3(XWPFTable table) {

        //混泥土等级
        String concreteGrade = Cantilever_3.CONCRETE_GRADE;
        MaterialProperty_1.ConcreteGrade concreteGrade1 = MaterialProperty_1.CONCRETE_GRADE_MAP.get(concreteGrade);

        //钢筋等级
        String steelGrade = Cantilever_3.STEEL_GRADE;
        MaterialProperty_1.SteelGrade steelGrade1 = MaterialProperty_1.STEEL_GRADE_MAP.get(steelGrade);

        Double _yre = 0.85;
        Double _afCV = 0.7;

        //3.根据公式进行计算
        Double h0 = Double.valueOf(Cantilever_3.SECTION_H) - Cantilever_3.PARAM_afS;
        Double _M_ = (Double.valueOf(Cantilever_3.FLOOR_H) - Cantilever_3.DAMPER_H) * Double.valueOf(Cantilever_3.STRESS_CONDITION_V0) * 0.5;
        Double _As_ = _M_ * 1000000 / (Double.valueOf(steelGrade1.fstk) * (h0 - Cantilever_3.PARAM_afS));
        Double _result_2 = (0.15 * concreteGrade1.fc * Double.valueOf(Cantilever_3.SECTION_B) * h0) / (_yre * 1000);
        Double _result_3 = ((0.6 * _afCV * concreteGrade1.ft * Double.valueOf(Cantilever_3.SECTION_B) * h0) +
                (Double.valueOf(steelGrade1.fyvk) * 4 * 0.25 * Math.PI * Math.pow(Double.valueOf(Cantilever_3.HOOP_D), 2) * h0 / Double.valueOf(Cantilever_3.HOOP_L))) / (_yre * 1000);

        System.out.println("=============== 计算结果 ===================");
        System.out.println("受剪验算 ：\n_M_  = " + _M_ + "\n_As_ = " + _As_ + "\n_result_2 =  " + _result_2 + "\n_result_3= " + _result_3);

        //4.比较数据，得出结论
        Double _As1_ = Double.valueOf(Cantilever_3.HOOP_VERTICL_COUNT) * 0.25 * Math.PI * Math.pow(Double.valueOf(Cantilever_3.HOOP_VERTICl_D), 2);

        String result;
        if (_As1_ > _As_ && _result_2 > Double.valueOf(Cantilever_3.STRESS_CONDITION_V0) && _result_3 > Double.valueOf(Cantilever_3.STRESS_CONDITION_V0)) {
            //通过
            result = "通过";
        } else {
            result = "不通过";
        }

        //4.将计算的结果回填到表格中
        Util.insertValueToCell(table.getRow(0).getCell(2), Util.getPrecisionString(Cantilever_3.SECTION_B, 1));
        Util.insertValueToCell(table.getRow(0).getCell(5), Util.getPrecisionString(Cantilever_3.SECTION_H, 1));
        Util.insertValueToCell(table.getRow(1).getCell(1), Util.getPrecisionString(Cantilever_3.FLOOR_H, 3));
        Util.insertValueToCell(table.getRow(1).getCell(5), Util.getPrecisionString(Cantilever_3.DAMPER_H, 3));
        Util.insertValueToCell(table.getRow(2).getCell(2), Util.getPrecisionString(Cantilever_3.STRESS_CONDITION_V0, 1));
        Util.insertValueToCell(table.getRow(2).getCell(5), Util.getPrecisionString(_M_.toString(), 1));

        Util.insertValueToCell(table.getRow(3).getCell(2), concreteGrade);
        Util.insertValueToCell(table.getRow(4).getCell(2), Util.getPrecisionString(concreteGrade1.fck, 1));
        Util.insertValueToCell(table.getRow(4).getCell(5), Util.getPrecisionString(Cantilever_3.PARAM_afS, 1));
        Util.insertValueToCell(table.getRow(5).getCell(2), Util.getPrecisionString(concreteGrade1.fc, 1));
        Util.insertValueToCell(table.getRow(5).getCell(5), Util.getPrecisionString(concreteGrade1.ftk, 1));

        Util.insertValueToCell(table.getRow(6).getCell(2), steelGrade);
        Util.insertValueToCell(table.getRow(7).getCell(2), Util.getPrecisionString(steelGrade1.fstk, 0));
        Util.insertValueToCell(table.getRow(7).getCell(5), Util.getPrecisionString(steelGrade1.fyvk, 0));

        Util.insertValueToCell(table.getRow(8).getCell(2), Util.getPrecisionString(Cantilever_3.HOOP_D, 0));
        Util.insertValueToCell(table.getRow(8).getCell(5), Util.getPrecisionString(Cantilever_3.HOOP_L, 0));

        Util.insertValueToCell(table.getRow(9).getCell(2), Util.getPrecisionString(Cantilever_3.HOOP_VERTICl_D, 0));
        Util.insertValueToCell(table.getRow(9).getCell(5), Util.getPrecisionString(Cantilever_3.HOOP_VERTICL_COUNT, 0));

        Util.insertValueToCell(table.getRow(10).getCell(2), Util.getPrecisionString(_As_, 0));
        Util.insertValueToCell(table.getRow(11).getCell(2), Util.getPrecisionString(_result_2, 0));
        Util.insertValueToCell(table.getRow(11).getCell(4), result);
        Util.insertValueToCell(table.getRow(12).getCell(2), Util.getPrecisionString(_result_3, 0));
    }
}
