package com.ui;

import com.mine.MainDeal;
import com.util.MyException;
import com.util.Util;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.util.Properties;

/**
 * 处理word 数据的 tab
 */
public class WordTab implements TabUI{

    //文件选择器，文件路径
    private TextField modelFilePathText;

    //第一行值
    private static TextField t11;
    private static TextField t12;
    private static TextField t13;

    //第二行值
    private static TextField t21;

    //第三行值
    private static TextField t31;
    private static TextField t32;

    //第四行值
    private static TextField t41;
    private static TextField t42;

    private static TextField[] tfs;

    //功能类型
    public static FunctionType FUNCTION_TYPE = FunctionType.YUNNAN_WORD;

    //执行步骤
    public static Step STEP;

    //是否导出
    public static Boolean IS_EXPORT;

    private static  String pathCache;
    private static Properties properties;
    private static String WORD_PATH = "wordPath";

    private Stage primaryStage;

    //进度条
    private ProgressBar p ;

    //执行结果
    private Label resultLabel;
    private Label resultLabel1;

    //执行结果
    private TextField textField;
    private TextField textField1;


    /**
     * 程序的功能类型
     */
    public enum FunctionType{
        YUNNAN_WORD("云南 "),
        JIANGSU_WORD("江苏 ");

        private String name;
        FunctionType(String name) {
            this.name = name;
        }
        public String getName() {
            return name;
        }
    }

    /**
     * 步骤
     */
    public enum Step{

        ONE(" 模型对比 "),
        TWO(" 地震波 "),
        THREE(" 附加阻尼比 "),
        FOUR(" 大震位移角 ");
        private String name;

        Step(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    static {
         properties = new Properties();
        InputStream inputStream = Object.class.getResourceAsStream("/cache.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        pathCache = properties.get(WORD_PATH).toString();
        System.out.println("上次操作的缓存路径:" + pathCache);
    }

    public static void updateProperties(String key,String value) throws IOException {
        InputStream in = Object.class.getResourceAsStream("/cache.properties");

//        URL url1 = WordTab.class.getClassLoader().getResource("/cache.properties");
        System.out.println("0000000000000");
//        System.out.println("u1"+url1.getPath());
        final ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        int ch;
        while ((ch = in.read()) != -1) {
            swapStream.write(ch);
        }
        try{
            Properties props = new Properties();
            props.setProperty(key, value);
            props.store(swapStream, key);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            swapStream.close();
        }
    }

    @Override
    public Tab getTab(Stage primaryStage) {
        this.primaryStage = primaryStage;
        Tab tab = new Tab("word数据处理");
        tab.setClosable(false);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 5, 5, 10));
        tab.setContent(grid);

        //功能模式 ，单选按钮
        singleSelect(grid);

        //文件选择器
        chooseFileButton(primaryStage,grid);

        //数据展示，
        addDataLabel(grid);

        //执行按钮
        executeButtons(grid);
        return tab;
    }



    /**
     * 单选按钮
     * @return
     */
    private void singleSelect(GridPane grid){

        GridPane g = new GridPane();

        ToggleGroup group = new ToggleGroup();
        int i = 0;
        for (FunctionType value : FunctionType.values()) {
            RadioButton b = new RadioButton(value.getName());
            b.setUserData(value);
            b.setToggleGroup(group);
            if (i == 0){
                b.setSelected(true);
            }
            g.add(b,i++,0);
        }

        grid.add(g,0,0);

        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            Toggle selectedToggle = group.getSelectedToggle();
            FUNCTION_TYPE = (FunctionType) selectedToggle.getUserData();
        });
    }


    /**
     * 文件选择器
     * @param primaryStage
     * @return
     */
    private void chooseFileButton(Stage primaryStage,GridPane grid){

        GridPane g = new GridPane();

        Label userName = new Label("  模板路径:  ");
        g.add(userName, 0, 0);
        modelFilePathText = new TextField();
        modelFilePathText.setPrefWidth(500);
        if (pathCache != null && pathCache.length() > 7) {
            modelFilePathText.setText(pathCache);
        }
        g.add(modelFilePathText, 1, 0);

        Button btn = new Button();
        btn.setText("选择模板");
        btn.setOnAction(event -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("选择word模板");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("word","*.docx"));
            String path = fileChooser.showOpenDialog(primaryStage).getPath();
            modelFilePathText.setText(path);
        });
        g.add(btn,2,0);

        grid.add(g,0,1);
    }


    /**
     * 添加数据信息
     * @param grid
     */
    private void addDataLabel(GridPane grid) {

        ToggleGroup group = new ToggleGroup();
        group.selectedToggleProperty().addListener((ov, old_toggle, new_toggle) -> {
            Toggle selectedToggle = group.getSelectedToggle();
            STEP = (Step) selectedToggle.getUserData();
        });

        //=======================================================================

        int column = 0;
        int row = 2;
        RadioButton r1 = getRadioButton(Step.ONE, group);
        GridPane g1 = new GridPane();
        g1.add(r1, column++, 0);

        g1.add(new Label("  质 量 : "), column++, row);
        t11 = new TextField();
        t11.setPrefWidth(80);
        t11.setEditable(false);
        g1.add(t11, column++, 2);

        g1.add(new Label("      周 期 : "), column++, row);
        t12 = new TextField();
        t12.setPrefWidth(180);
        t12.setEditable(false);
        g1.add(t12, column++, 2);

        g1.add(new Label("      剪 力 : "), column++, row);
        t13 = new TextField();
        t13.setPrefWidth(130);
        t13.setEditable(false);
        g1.add(t13, column++, row);

        //=======================================================================

        column = 0;
        RadioButton r2 = getRadioButton(Step.TWO, group);
        GridPane g2 = new GridPane();
        g2.add(r2, column++, 0);

        g2.add(new Label("   地震波 : "), column++, row);
        t21 = new TextField();
        t21.setPrefWidth(130);
        t21.setEditable(false);
        g2.add(t21, column++, 2);

        //=======================================================================

        column = 0;
        RadioButton r3 = getRadioButton(Step.THREE, group);
        GridPane g3 = new GridPane();
        g3.add(r3, column++, 0);

        g3.add(new Label("X 向 : "), column++, row);
        t31 = new TextField();
        t31.setPrefWidth(130);
        t31.setEditable(false);
        g3.add(t31, column++, 2);

        g3.add(new Label("      Y 向 : "), column++, row);
        t32 = new TextField();
        t32.setPrefWidth(130);
        t32.setEditable(false);
        g3.add(t32, column++, 2);

        //=======================================================================

        column = 0;
        RadioButton r4 = getRadioButton(Step.FOUR, group);
        GridPane g4 = new GridPane();
        g4.add(r4, column++, 0);

        g4.add(new Label("X 向 : "), column++, row);
        t41 = new TextField();
        t41.setPrefWidth(130);
        t41.setEditable(false);
        g4.add(t41, column++, 2);

        g4.add(new Label("      Y 向 : "), column++, row);
        t42 = new TextField();
        t42.setPrefWidth(130);
        t42.setEditable(false);
        g4.add(t42, column++, 2);

        //=======================================================================

        tfs = new TextField[]{t11,t12,t13,t21,t31,t32,t41,t42};

        grid.add(g1, 0, 5);
        grid.add(g2, 0, 6);
        grid.add(g3, 0, 7);
        grid.add(g4, 0, 8);
    }


    /**
     * 获取单选组件
     * @param step
     * @param group
     * @return
     */
    private RadioButton getRadioButton(Step step,ToggleGroup group){
        RadioButton b = new RadioButton(step.getName());
        b.setUserData(step);
        b.setToggleGroup(group);
        return b;
    }


    /**
     * 添加按钮
     * @param grid
     */
    private void executeButtons(GridPane grid){


        GridPane g = new GridPane();
        g.setAlignment(Pos.BOTTOM_CENTER);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(5, 5, 5, 10));
        Button b1 = new Button();
        b1.setText("执 行 程 序");
        b1.setOnAction(event -> {
            IS_EXPORT = false;
            b1.setDisable(true);
            execute();
            b1.setDisable(false);
        });

        Button b2 = new Button();
        b2.setText("结 果 输 出");
        b2.setOnAction(event -> {
            IS_EXPORT = true;
            b2.setDisable(true);
            execute();
            b2.setDisable(false);
        });

        resultLabel = new Label("");
        resultLabel.setVisible(false);
        textField = new TextField();
        textField.setVisible(false);
        textField.setPrefWidth(400);

        resultLabel1 = new Label("");
        resultLabel1.setVisible(false);
        textField1 = new TextField();
        textField1.setVisible(false);
        textField1.setPrefWidth(400);

        p =new ProgressBar();
        p.setVisible(false);
        p.setDisable(true);

        g.add(p,1,0);
        g.add(b1,0,1);
        g.add(b2,2,1);
        grid.add(g,0,9);

        GridPane gg = new GridPane();
        gg.add(resultLabel,1,0);
        gg.add(textField,2,0);
        gg.add(resultLabel1,1,1);
        gg.add(textField1,2,1);
//        GridPane ggg = new GridPane();
//        ggg.add(textField1,2,0);
        grid.add(gg,0,10);
//        grid.add(ggg,0,11);
    }

    /**
     * 执行按钮点击操作
     */
    private void execute() {

        clearInfo();

        String path = modelFilePathText.getText();
        if (Util.stringIsBlack(path)){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("请选择模板文件");
            alert.show();
            return;
        }

        p.setVisible(true);
        p.setDisable(false);

        Task task = new Task<Object>() {
            @Override
            protected Object call() {
                Exception ex = null;
                try {
                    MainDeal.getBasePath(path);
                } catch (Exception e) {
                    ex = e;
                    e.printStackTrace();
                }

                Exception exx = ex;
                Platform.runLater(() -> {
                    if (exx == null) {
                        resultLabel.setText("   执行完成！");
                    } else {
                        resultLabel.setText("   运行异常    错误信息 ：");
                        resultLabel1.setText("                    异常位置 ：");
                        resultLabel1.setVisible(true);
                        Exception ev = exx;

                        if (exx instanceof MyException){
                            textField1.setVisible(true);
                            textField1.setText(((MyException) exx).getFunctionName());
                            ev = ((MyException) exx).getE();
                        }
                        String errorMessage = ev instanceof NullPointerException ? "空指针异常":
                                ev instanceof FileNotFoundException ? "文件找不到:"+ev.getMessage() : ev.getMessage();
                        textField.setText(errorMessage);
                        textField.setVisible(true);
                    }

                    primaryStage.hide();
                    primaryStage.show();
                });
                resultLabel.setVisible(true);
                p.setVisible(false);
                p.setDisable(true);
                return null;
            }
        };

        p.progressProperty().bind(task.progressProperty());
        new Thread(()->task.run()).start();
    }

    /**
     * 清空
     */
    private void clearInfo(){
        t11.setText("");
        t12.setText("");
        t13.setText("");
        t21.setText("");
        t31.setText("");
        t32.setText("");
        t41.setText("");
        t42.setText("");
        resultLabel.setVisible(false);
        resultLabel.setText("");
        resultLabel1.setVisible(false);
        resultLabel1.setText("");
        textField.setText("");
        textField.setVisible(false);
        textField1.setText("");
        textField1.setVisible(false);

    }

    public static void setV11(String value){
        t11.setText(value);
        t11.impl_updatePeer();
    }

    public static void setV12(String value){
        t12.setText(value);
        t11.impl_updatePeer();
    }


    public static void setV13(String value){
        t13.setText(value);
        t11.impl_updatePeer();
    }

    public static void setV21(String value){
        t21.setText(value);
    }

    public static void setV31(String value){
        t31.setText(value);
    }

    public static void setV32(String value){
        t32.setText(value);
    }

    public static void setV41(String value){
        t41.setText(value);
    }

    public static void setV42(String value){
        t42.setText(value);
    }
}
