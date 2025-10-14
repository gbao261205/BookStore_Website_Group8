<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%@ taglib prefix="fn"  uri="jakarta.tags.functions" %>
<%@ taglib prefix="app" tagdir="/WEB-INF/tags" %>
<fmt:setLocale value="vi_VN"/>
<c:url var="cssMain"   value="/styles/main.css"/>
<c:url var="cssNavbar" value="/styles/navbar.css"/>
<c:url var="banner1"   value="/image/banner1.jpg"/>
<c:url var="banner2"   value="/image/banner2.jpg"/>
<c:url var="banner3"   value="/image/banner3.jpg"/>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>BookStore | Trang chủ</title>
        <link rel="stylesheet" href="${cssMain}"/>
        <link rel="stylesheet" href="${cssNavbar}"/>
    </head>
    <body>
        <jsp:include page="WEB-INF/partials/navbar.jsp"/>
        
        <section class="hero">
  <div class="container wrap">
    <div class="track" id="slider">
      <div class="slide"><img src="${banner1}" alt="Banner 1"></div>
      <div class="slide"><img src="${banner2}" alt="Banner 2"></div>
      <div class="slide"><img src="${banner3}" alt="Banner 3"></div>
    </div>

    <button type="button" class="nav prev" id="prev" aria-label="Trước">&#10094;</button>
    <button type="button" class="nav next" id="next" aria-label="Sau">&#10095;</button>

    <div class="dots" id="dots"></div>
  </div>
</section>

        
        <section class="quick">
            <div class="container quick__wrap">
                <button class="quick__item" data-jump="#bestBooksSec"><span class="quick__icon">🔥</span><span>Sách bán chạy</span></button>
                <button class="quick__item" data-jump="#newBooksSec"><span class="quick__icon">📚</span><span>Sách mới</span></button>
                <button class="quick__item" data-jump="#suggestBooksSec"><span class="quick__icon">✨</span><span>Gợi ý cho bạn</span></button>
            </div>
        </section>
                
        <main class="container">
            <section id="newBooksSec" class="bk-sec horizontal">
                <div class="bk-sec__head">
                    <h2 class="bk-title-lg">📚 Sách mới</h2>
                    <a class="view-more" href="${pageContext.request.contextPath}/books?type=new">Xem thêm →</a>
                </div>
                <app:booksGrid title="" items="${newBooks}" emptyText="Chưa có dữ liệu." detailUrl="${detailUrl}" showAddButton="true"/>
            </section>

            <section id="bestBooksSec" class="bk-sec horizontal">
                <div class="bk-sec__head">
                    <h2 class="bk-title-lg">🔥 Sách bán chạy</h2>
                    <a class="view-more" href="${pageContext.request.contextPath}/books?type=best">Xem thêm →</a>
                </div>
                <app:booksGrid title="" items="${bestBooks}" emptyText="Chưa có dữ liệu." detailUrl="${detailUrl}" showAddButton="true"/>
            </section>

            <section id="suggestBooksSec" class="bk-sec horizontal">
                <div class="bk-sec__head">
                    <h2 class="bk-title-lg">✨ Gợi ý cho bạn</h2>
                    <a class="view-more" href="${pageContext.request.contextPath}/books?type=suggest">Xem thêm →</a>
                </div>
                <app:booksGrid title="" items="${suggestBooks}" emptyText="Chưa có dữ liệu." detailUrl="${detailUrl}" showAddButton="true"/>
            </section>

        </main>
            
        <script>
document.querySelectorAll(".quick__item").forEach(btn => {
  btn.addEventListener("click", () => {
    const targetId = btn.getAttribute("data-jump");
    const section = document.querySelector(targetId);
    if (section) {
      section.scrollIntoView({ behavior: "smooth" });
    }
  });
});
        </script>
       <script>
document.addEventListener("DOMContentLoaded", function() {
  const track = document.querySelector(".track");
  const slides = document.querySelectorAll(".slide");
  const prevBtn = document.getElementById("prev");
  const nextBtn = document.getElementById("next");

  if (!track || slides.length === 0) return;

  let index = 0;
  const totalSlides = slides.length;
  const duration = 600; // ms

  // Thiết lập ban đầu
  track.style.display = "flex";
  track.style.transition = `transform ${duration}ms ease`;
  slides.forEach(slide => slide.style.flex = "0 0 100%");

  function updateSlide() {
    track.style.transform = `translateX(-${index * 100}%)`;
  }

  nextBtn.addEventListener("click", e => {
    e.preventDefault();
    e.stopPropagation();
    index = (index + 1) % totalSlides; // quay vòng
    updateSlide();
  });

  prevBtn.addEventListener("click", e => {
    e.preventDefault();
    e.stopPropagation();
    index = (index - 1 + totalSlides) % totalSlides; // quay vòng
    updateSlide();
  });

  // Hiển thị slide đầu tiên
  updateSlide();
});
</script>



    </body>
</html>
