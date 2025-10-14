<%@ tag language="java" pageEncoding="UTF-8" trimDirectiveWhitespaces="true" description="Single book card" %>
<%@ taglib uri="jakarta.tags.core"      prefix="c"  %>
<%@ taglib uri="jakarta.tags.fmt"       prefix="fmt"%>
<%@ taglib uri="jakarta.tags.functions" prefix="fn" %>

<%@ attribute name="book"           required="true"  type="Model.Book" %>
<%@ attribute name="detailUrl"      required="false" type="java.lang.String" %>
<%@ attribute name="showAddButton"  required="false" type="java.lang.Boolean" %>
<%@ attribute name="badge"          required="false" type="java.lang.String" %>

<!-- Lấy ảnh bìa: nếu cover là link tuyệt đối thì dùng luôn, nếu là file cục bộ thì nối uploads/, còn trống thì placeholder -->
<c:set var="raw" value="${book.coverUrl}" />

<c:choose>
  <c:when test="${not empty raw and (fn:startsWith(raw,'http://') or fn:startsWith(raw,'https://') or fn:startsWith(raw,'//'))}">
    <c:set var="imgSrc" value="${raw}" />
  </c:when>
  <c:when test="${not empty raw}">
    <c:set var="imgSrc" value="${pageContext.request.contextPath}/uploads/${raw}" />
  </c:when>
  <c:otherwise>
    <c:set var="imgSrc" value="${pageContext.request.contextPath}/image/placeholder.jpg" />
  </c:otherwise>
</c:choose>

<c:url var="detail" value="${empty detailUrl ? '/book' : detailUrl}">
  <c:param name="id" value="${book.id}" />
</c:url>

<div class="bk-card">
  <a class="bk-card__cover" href="${detail}">
    <img src="${imgSrc}" alt="${fn:escapeXml(book.title)}" loading="lazy"/>
  </a>

  <div class="bk-card__body">
    <div class="bk-card__title">${book.title}</div>
    <div class="bk-card__author">${book.author}</div>
    <div class="bk-card__meta">
      <div class="bk-card__price">
        <fmt:formatNumber value="${book.price}" type="currency" currencySymbol="₫" maxFractionDigits="0"/>
      </div>
      <c:if test="${not empty badge}">
        <span class="bk-badge">${badge}</span>
      </c:if>
    </div>
    <c:if test="${showAddButton == null || showAddButton}">
      <button class="bk-btn"
              onclick="addToCart({id:${book.id},title:'${fn:escapeXml(book.title)}',img:'${imgSrc}',price:${book.price}})">
        Thêm
      </button>
    </c:if>
  </div>
</div>
