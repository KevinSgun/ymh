package com.fans.becomebeaut.api.entity;

public  class ExchangeItem {
        private int id;
        private int inventory;
        private int mold;
        private String name;
        private int oldprice;
        private String portrait;
        private int price;
        private int sales;
        private int score;
        private String subtitle;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getInventory() {
            return inventory;
        }

        public void setInventory(int inventory) {
            this.inventory = inventory;
        }

        public int getMold() {
            return mold;
        }

        public void setMold(int mold) {
            this.mold = mold;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getOldprice() {
            return oldprice;
        }

        public void setOldprice(int oldprice) {
            this.oldprice = oldprice;
        }

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getSales() {
            return sales;
        }

        public void setSales(int sales) {
            this.sales = sales;
        }

        public int getScore() {
            return score;
        }

        public void setScore(int score) {
            this.score = score;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }
    }