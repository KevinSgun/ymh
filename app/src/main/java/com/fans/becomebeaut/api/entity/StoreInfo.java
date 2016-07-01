package com.fans.becomebeaut.api.entity;

public  class StoreInfo {
        private int id;
        private String name;
        private String corp;
        private String phone;
        private String address;
        private String icon;
        private int allComment;
        private int perfectCount;
        private int goodCount;
        private int badCount;
        private int isStoreUp;
        private String latitude;
        private String longitude;
        private String start;
        private String expired;
        private String satisfactory;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCorp() {
            return corp;
        }

        public void setCorp(String corp) {
            this.corp = corp;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public int getAllComment() {
            return allComment;
        }

        public void setAllComment(int allComment) {
            this.allComment = allComment;
        }

        public int getPerfectCount() {
            return perfectCount;
        }

        public void setPerfectCount(int perfectCount) {
            this.perfectCount = perfectCount;
        }

        public int getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(int goodCount) {
            this.goodCount = goodCount;
        }

        public int getBadCount() {
            return badCount;
        }

        public void setBadCount(int badCount) {
            this.badCount = badCount;
        }

        public int getIsStoreUp() {
            return isStoreUp;
        }

        public void setIsStoreUp(int isStoreUp) {
            this.isStoreUp = isStoreUp;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getStart() {
            return start;
        }

        public void setStart(String start) {
            this.start = start;
        }

        public String getExpired() {
            return expired;
        }

        public void setExpired(String expired) {
            this.expired = expired;
        }

        public String getSatisfactory() {
            return satisfactory;
        }

        public void setSatisfactory(String satisfactory) {
            this.satisfactory = satisfactory;
        }
    }