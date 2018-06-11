package com.duolebo.appbase.prj.bmtv.model;

public class ReportStatusData extends ModelBase {

    public static enum OperType {
        StartAppError("-4"),
        CheckMd5Error("-3"),
        InstallError("-2"),
        DownloadError("-1"),
        DownloadSucc("1"),
        InstallSucc("2"),
        StartAppSucc("3"),
        Unknown("Unknown");

        private String val;

        OperType(String val) {
            this.val = val;
        }

        public String toString() {
            return this.val;
        }

        public static OperType fromString(String val) {
            for (OperType at : OperType.values()) {
                if (at.val.equalsIgnoreCase(val)) {
                    return at;
                }
            }
            return Unknown;
        }

    }

}
