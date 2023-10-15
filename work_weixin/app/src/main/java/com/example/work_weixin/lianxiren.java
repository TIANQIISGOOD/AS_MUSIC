package com.example.work_weixin;

import java.io.Serializable;

//展示联系人,包含联系人的昵称,微信号,地区
public class lianxiren implements Serializable {
        private String name;
        private String number;
        private String area;

        public lianxiren(String name,String number,String area)
        {
            this.name=name;
            this.number=number;
            this.area=area;
        }


}
