package com.duolebo.appbase.prj.bmtv.model;

import com.duolebo.appbase.prj.Model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * @author advu
 * @date 2016年11月14日
 *       获取 订单list
 */
public class GetOrderListData extends ModelBase {

    private ArrayList<GetOrderListData.Content> contentlist = new ArrayList<GetOrderListData.Content>();

    @Override
    public boolean from(JSONObject json) {
        if (super.from(json)) {
	        json = json.optJSONObject("response").optJSONObject("body");
            JSONArray ja = json.optJSONArray("orderlist");
            if (null != ja) {
                for (int i=0; i<ja.length(); i++) {
                    JSONObject jo = ja.optJSONObject(i);
                    GetOrderListData.Content content = new GetOrderListData.Content();
                    content.from(jo);
                    contentlist.add(content);
                }
            }
            return true;
        }
        return false;
    }

    public ArrayList<GetOrderListData.Content> getContentList() {
        return contentlist;
    }

    public static class Content extends Model {
        private String order_type = "";
        private String tariff_name = "";
        private String tariff_type = "";
        private String c_name = "";
        private String c_desc = "";
        private String num = "";
        private String price = "";
        private String money_sum = "";
        private String remark = "";
        private String pay_type = "";
        private String order_status = "";
        private String pay_status = "";
        private String pay_time = "";

        @Override
        public boolean from(JSONObject json) {
            if (super.from(json)) {
                order_type = json.optString("order_type");
                tariff_name = json.optString("tariff_name");
                tariff_type = json.optString("tariff_type");
                if(order_type .equals("0")){
                    c_name = json.optString("c_name");
                    c_desc = json.optString("c_desc");
                }
                num = json.optString("num");
                price = json.optString("price");
                money_sum = json.optString("money_sum");
                remark = json.optString("remark");
                pay_type = json.optString("pay_type");
                order_status = json.optString("order_status");
                pay_status = json.optString("pay_status");
                pay_time = json.optString("pay_time");

                return true;
            }
            return false;
        }

        public String getOrder_type() {
            return order_type;
        }

        public void setOrder_type(String order_type) {
            this.order_type = order_type;
        }

        public String getTariff_name() {
            return tariff_name;
        }

        public void setTariff_name(String tariff_name) {
            this.tariff_name = tariff_name;
        }

        public String getTariff_type() {
            return tariff_type;
        }

        public void setTariff_type(String tariff_type) {
            this.tariff_type = tariff_type;
        }

        public String getC_name() {
            return c_name;
        }

        public void setC_name(String c_name) {
            this.c_name = c_name;
        }

        public String getC_desc() {
            return c_desc;
        }

        public void setC_desc(String c_desc) {
            this.c_desc = c_desc;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getMoney_sum() {
            return money_sum;
        }

        public void setMoney_sum(String money_sum) {
            this.money_sum = money_sum;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        public String getPay_type() {
            return pay_type;
        }

        public void setPay_type(String pay_type) {
            this.pay_type = pay_type;
        }

        public String getOrder_status() {
            return order_status;
        }

        public void setOrder_status(String order_status) {
            this.order_status = order_status;
        }

        public String getPay_status() {
            return pay_status;
        }

        public void setPay_status(String pay_status) {
            this.pay_status = pay_status;
        }

        public String getPay_time() {
            return pay_time;
        }

        public void setPay_time(String pay_time) {
            this.pay_time = pay_time;
        }
    }


}
