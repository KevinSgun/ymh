package com.fans.becomebeaut.api.entity;

public  class ReviewHead {
        private int allComment;
        private int badCount;
        private int goodCount;
        private int perfectCount;
        private String satisfactory;

        public int getAllComment() {
            return allComment;
        }

        public void setAllComment(int allComment) {
            this.allComment = allComment;
        }

        public int getBadCount() {
            return badCount;
        }

        public void setBadCount(int badCount) {
            this.badCount = badCount;
        }

        public int getGoodCount() {
            return goodCount;
        }

        public void setGoodCount(int goodCount) {
            this.goodCount = goodCount;
        }

        public int getPerfectCount() {
            return perfectCount;
        }

        public void setPerfectCount(int perfectCount) {
            this.perfectCount = perfectCount;
        }

        public String getSatisfactory() {
            return satisfactory;
        }

        public void setSatisfactory(String satisfactory) {
            this.satisfactory = satisfactory;
        }
    }
