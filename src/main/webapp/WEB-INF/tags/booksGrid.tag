<%@ tag description="Books grid section" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"      prefix="c"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>

<%@ attribute name="title"         required="false" type="java.lang.String" %>
<%@ attribute name="items"         required="true"  type="java.util.List"   %>
<%@ attribute name="emptyText"     required="false" type="java.lang.String" %>
<%@ attribute name="detailUrl"     required="false" type="java.lang.String" %>
<%@ attribute name="badge"         required="false" type="java.lang.String" %>
<%@ attribute name="showAddButton" required="false" type="java.lang.Boolean" %>

<div class="bk-sec">
  <c:if test="${not empty title}">
    <div class="bk-sec__head">
      <div class="bk-title-lg">${title}</div>
    </div>
  </c:if>

  <c:choose>
    <c:when test="${empty items}">
      <div class="placeholder">${emptyText}</div>
    </c:when>
    <c:otherwise>
      <div class="bk-grid">
        <c:forEach var="b" items="${items}">
          <app:bookCard book="${b}"
                        detailUrl="${detailUrl}"
                        showAddButton="${showAddButton}"
                        badge="${badge}" />
        </c:forEach>
      </div>
    </c:otherwise>
  </c:choose>
</div>