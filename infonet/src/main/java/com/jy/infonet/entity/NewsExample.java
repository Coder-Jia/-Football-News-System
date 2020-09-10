package com.jy.infonet.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NewsExample {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    public NewsExample() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andNewsIdIsNull() {
            addCriterion("news_id is null");
            return (Criteria) this;
        }

        public Criteria andNewsIdIsNotNull() {
            addCriterion("news_id is not null");
            return (Criteria) this;
        }

        public Criteria andNewsIdEqualTo(Integer value) {
            addCriterion("news_id =", value, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdNotEqualTo(Integer value) {
            addCriterion("news_id <>", value, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdGreaterThan(Integer value) {
            addCriterion("news_id >", value, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("news_id >=", value, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdLessThan(Integer value) {
            addCriterion("news_id <", value, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdLessThanOrEqualTo(Integer value) {
            addCriterion("news_id <=", value, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdIn(List<Integer> values) {
            addCriterion("news_id in", values, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdNotIn(List<Integer> values) {
            addCriterion("news_id not in", values, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdBetween(Integer value1, Integer value2) {
            addCriterion("news_id between", value1, value2, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsIdNotBetween(Integer value1, Integer value2) {
            addCriterion("news_id not between", value1, value2, "newsId");
            return (Criteria) this;
        }

        public Criteria andNewsTitleIsNull() {
            addCriterion("news_title is null");
            return (Criteria) this;
        }

        public Criteria andNewsTitleIsNotNull() {
            addCriterion("news_title is not null");
            return (Criteria) this;
        }

        public Criteria andNewsTitleEqualTo(String value) {
            addCriterion("news_title =", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleNotEqualTo(String value) {
            addCriterion("news_title <>", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleGreaterThan(String value) {
            addCriterion("news_title >", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleGreaterThanOrEqualTo(String value) {
            addCriterion("news_title >=", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleLessThan(String value) {
            addCriterion("news_title <", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleLessThanOrEqualTo(String value) {
            addCriterion("news_title <=", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleLike(String value) {
            addCriterion("news_title like", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleNotLike(String value) {
            addCriterion("news_title not like", value, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleIn(List<String> values) {
            addCriterion("news_title in", values, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleNotIn(List<String> values) {
            addCriterion("news_title not in", values, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleBetween(String value1, String value2) {
            addCriterion("news_title between", value1, value2, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsTitleNotBetween(String value1, String value2) {
            addCriterion("news_title not between", value1, value2, "newsTitle");
            return (Criteria) this;
        }

        public Criteria andNewsIntroIsNull() {
            addCriterion("news_intro is null");
            return (Criteria) this;
        }

        public Criteria andNewsIntroIsNotNull() {
            addCriterion("news_intro is not null");
            return (Criteria) this;
        }

        public Criteria andNewsIntroEqualTo(String value) {
            addCriterion("news_intro =", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroNotEqualTo(String value) {
            addCriterion("news_intro <>", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroGreaterThan(String value) {
            addCriterion("news_intro >", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroGreaterThanOrEqualTo(String value) {
            addCriterion("news_intro >=", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroLessThan(String value) {
            addCriterion("news_intro <", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroLessThanOrEqualTo(String value) {
            addCriterion("news_intro <=", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroLike(String value) {
            addCriterion("news_intro like", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroNotLike(String value) {
            addCriterion("news_intro not like", value, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroIn(List<String> values) {
            addCriterion("news_intro in", values, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroNotIn(List<String> values) {
            addCriterion("news_intro not in", values, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroBetween(String value1, String value2) {
            addCriterion("news_intro between", value1, value2, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsIntroNotBetween(String value1, String value2) {
            addCriterion("news_intro not between", value1, value2, "newsIntro");
            return (Criteria) this;
        }

        public Criteria andNewsDateIsNull() {
            addCriterion("news_date is null");
            return (Criteria) this;
        }

        public Criteria andNewsDateIsNotNull() {
            addCriterion("news_date is not null");
            return (Criteria) this;
        }

        public Criteria andNewsDateEqualTo(Date value) {
            addCriterion("news_date =", value, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateNotEqualTo(Date value) {
            addCriterion("news_date <>", value, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateGreaterThan(Date value) {
            addCriterion("news_date >", value, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateGreaterThanOrEqualTo(Date value) {
            addCriterion("news_date >=", value, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateLessThan(Date value) {
            addCriterion("news_date <", value, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateLessThanOrEqualTo(Date value) {
            addCriterion("news_date <=", value, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateIn(List<Date> values) {
            addCriterion("news_date in", values, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateNotIn(List<Date> values) {
            addCriterion("news_date not in", values, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateBetween(Date value1, Date value2) {
            addCriterion("news_date between", value1, value2, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsDateNotBetween(Date value1, Date value2) {
            addCriterion("news_date not between", value1, value2, "newsDate");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseIsNull() {
            addCriterion("news_browse is null");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseIsNotNull() {
            addCriterion("news_browse is not null");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseEqualTo(Integer value) {
            addCriterion("news_browse =", value, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseNotEqualTo(Integer value) {
            addCriterion("news_browse <>", value, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseGreaterThan(Integer value) {
            addCriterion("news_browse >", value, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseGreaterThanOrEqualTo(Integer value) {
            addCriterion("news_browse >=", value, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseLessThan(Integer value) {
            addCriterion("news_browse <", value, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseLessThanOrEqualTo(Integer value) {
            addCriterion("news_browse <=", value, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseIn(List<Integer> values) {
            addCriterion("news_browse in", values, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseNotIn(List<Integer> values) {
            addCriterion("news_browse not in", values, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseBetween(Integer value1, Integer value2) {
            addCriterion("news_browse between", value1, value2, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsBrowseNotBetween(Integer value1, Integer value2) {
            addCriterion("news_browse not between", value1, value2, "newsBrowse");
            return (Criteria) this;
        }

        public Criteria andNewsCoverIsNull() {
            addCriterion("news_cover is null");
            return (Criteria) this;
        }

        public Criteria andNewsCoverIsNotNull() {
            addCriterion("news_cover is not null");
            return (Criteria) this;
        }

        public Criteria andNewsCoverEqualTo(String value) {
            addCriterion("news_cover =", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverNotEqualTo(String value) {
            addCriterion("news_cover <>", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverGreaterThan(String value) {
            addCriterion("news_cover >", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverGreaterThanOrEqualTo(String value) {
            addCriterion("news_cover >=", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverLessThan(String value) {
            addCriterion("news_cover <", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverLessThanOrEqualTo(String value) {
            addCriterion("news_cover <=", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverLike(String value) {
            addCriterion("news_cover like", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverNotLike(String value) {
            addCriterion("news_cover not like", value, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverIn(List<String> values) {
            addCriterion("news_cover in", values, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverNotIn(List<String> values) {
            addCriterion("news_cover not in", values, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverBetween(String value1, String value2) {
            addCriterion("news_cover between", value1, value2, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsCoverNotBetween(String value1, String value2) {
            addCriterion("news_cover not between", value1, value2, "newsCover");
            return (Criteria) this;
        }

        public Criteria andNewsLikeIsNull() {
            addCriterion("news_like is null");
            return (Criteria) this;
        }

        public Criteria andNewsLikeIsNotNull() {
            addCriterion("news_like is not null");
            return (Criteria) this;
        }

        public Criteria andNewsLikeEqualTo(Integer value) {
            addCriterion("news_like =", value, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeNotEqualTo(Integer value) {
            addCriterion("news_like <>", value, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeGreaterThan(Integer value) {
            addCriterion("news_like >", value, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeGreaterThanOrEqualTo(Integer value) {
            addCriterion("news_like >=", value, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeLessThan(Integer value) {
            addCriterion("news_like <", value, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeLessThanOrEqualTo(Integer value) {
            addCriterion("news_like <=", value, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeIn(List<Integer> values) {
            addCriterion("news_like in", values, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeNotIn(List<Integer> values) {
            addCriterion("news_like not in", values, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeBetween(Integer value1, Integer value2) {
            addCriterion("news_like between", value1, value2, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsLikeNotBetween(Integer value1, Integer value2) {
            addCriterion("news_like not between", value1, value2, "newsLike");
            return (Criteria) this;
        }

        public Criteria andNewsContentIsNull() {
            addCriterion("news_content is null");
            return (Criteria) this;
        }

        public Criteria andNewsContentIsNotNull() {
            addCriterion("news_content is not null");
            return (Criteria) this;
        }

        public Criteria andNewsContentEqualTo(String value) {
            addCriterion("news_content =", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentNotEqualTo(String value) {
            addCriterion("news_content <>", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentGreaterThan(String value) {
            addCriterion("news_content >", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentGreaterThanOrEqualTo(String value) {
            addCriterion("news_content >=", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentLessThan(String value) {
            addCriterion("news_content <", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentLessThanOrEqualTo(String value) {
            addCriterion("news_content <=", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentLike(String value) {
            addCriterion("news_content like", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentNotLike(String value) {
            addCriterion("news_content not like", value, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentIn(List<String> values) {
            addCriterion("news_content in", values, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentNotIn(List<String> values) {
            addCriterion("news_content not in", values, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentBetween(String value1, String value2) {
            addCriterion("news_content between", value1, value2, "newsContent");
            return (Criteria) this;
        }

        public Criteria andNewsContentNotBetween(String value1, String value2) {
            addCriterion("news_content not between", value1, value2, "newsContent");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNull() {
            addCriterion("type_id is null");
            return (Criteria) this;
        }

        public Criteria andTypeIdIsNotNull() {
            addCriterion("type_id is not null");
            return (Criteria) this;
        }

        public Criteria andTypeIdEqualTo(Integer value) {
            addCriterion("type_id =", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotEqualTo(Integer value) {
            addCriterion("type_id <>", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThan(Integer value) {
            addCriterion("type_id >", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("type_id >=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThan(Integer value) {
            addCriterion("type_id <", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdLessThanOrEqualTo(Integer value) {
            addCriterion("type_id <=", value, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdIn(List<Integer> values) {
            addCriterion("type_id in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotIn(List<Integer> values) {
            addCriterion("type_id not in", values, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdBetween(Integer value1, Integer value2) {
            addCriterion("type_id between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andTypeIdNotBetween(Integer value1, Integer value2) {
            addCriterion("type_id not between", value1, value2, "typeId");
            return (Criteria) this;
        }

        public Criteria andNewsCommentIsNull() {
            addCriterion("news_comment is null");
            return (Criteria) this;
        }

        public Criteria andNewsCommentIsNotNull() {
            addCriterion("news_comment is not null");
            return (Criteria) this;
        }

        public Criteria andNewsCommentEqualTo(Integer value) {
            addCriterion("news_comment =", value, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentNotEqualTo(Integer value) {
            addCriterion("news_comment <>", value, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentGreaterThan(Integer value) {
            addCriterion("news_comment >", value, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentGreaterThanOrEqualTo(Integer value) {
            addCriterion("news_comment >=", value, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentLessThan(Integer value) {
            addCriterion("news_comment <", value, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentLessThanOrEqualTo(Integer value) {
            addCriterion("news_comment <=", value, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentIn(List<Integer> values) {
            addCriterion("news_comment in", values, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentNotIn(List<Integer> values) {
            addCriterion("news_comment not in", values, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentBetween(Integer value1, Integer value2) {
            addCriterion("news_comment between", value1, value2, "newsComment");
            return (Criteria) this;
        }

        public Criteria andNewsCommentNotBetween(Integer value1, Integer value2) {
            addCriterion("news_comment not between", value1, value2, "newsComment");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}