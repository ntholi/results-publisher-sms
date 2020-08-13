module com.breakoutms.luct.reg {
    requires javafx.controls;
    requires javafx.fxml;
	requires org.apache.commons.collections4;
	requires poi;
	requires org.apache.commons.lang3;
	requires lombok;
	requires javafx.graphics;

    opens com.breakoutms.luct.reg to javafx.fxml;
    exports com.breakoutms.luct.reg;
}