package com.web.whatashot.fileupload;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServerResponse {

    @SerializedName("status")
    @Expose
    private boolean status;

    @SerializedName("msg")
    @Expose
    private String msg;

    @SerializedName("userdata")
    @Expose
    private UserData userdata;


    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }


    public void setMsg(String msg) {
        this.msg = msg;
    }

    public UserData getUserdata() {
        return userdata;
    }

    public void setUserdata(UserData userdata) {
        this.userdata = userdata;
    }

    public class UserData
    {
        @SerializedName("user_id")
        @Expose
        private String user_id;


        @SerializedName("profile_pic")
        @Expose
        private String profile_pic;

        @SerializedName("email")
        @Expose
        private String email;


        @SerializedName("first_name")
        @Expose
        private String first_name;


        @SerializedName("last_name")
        @Expose
        private String last_name;


        @SerializedName("mobile")
        @Expose
        private String mobile;


        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}



