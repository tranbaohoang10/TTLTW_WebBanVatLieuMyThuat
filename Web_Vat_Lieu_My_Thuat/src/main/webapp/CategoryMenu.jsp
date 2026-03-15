<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    /* Begin section-info-mithuat */
    #section-info-mithuat {
        padding-top: 100px;
        background: #E5EEFF;
    }

    #section-info-mithuat .container {
        position: relative;
    }

    #section-info-mithuat .info-mithuat-header {
        display: flex;
        justify-content: center;
        align-items: center;

        font-size: 18px;
        padding: 20px 0px;
        font-weight: 500;
    }

    /* them the a */

    a {
        text-decoration: none;
        color: black;

    }

    #section-info-mithuat .info-mithuat-header .info-mithuat-header-content {
        margin: 0 5px;
    }

    #section-info-mithuat .info-mithuat-header i:nth-child(1) {
        color: #F7D74E;
    }

    #section-info-mithuat .info-mithuat-header:hover {
        background-color: white;
        cursor: pointer;
    }

    #section-info-mithuat .info-mithuat-header:hover .info-mithuat-header-content,
    i:nth-child(2) {
        color: #17479D;
    }

    #section-info-mithuat .info-mithuat-header:hover .fa-angle-down {
        color: #17479D;
    }

    #section-info-mithuat .hover-block {
        display: none;
    }

    #section-info-mithuat .info-mithuat-header:hover + .hover-block {
        display: block;
    }

    /* Keep dropdown visible when hovering the header OR the dropdown itself */
    #section-info-mithuat .container:hover .hover-block,
    #section-info-mithuat .hover-block:hover {
        display: block;
    }

    #section-info-mithuat .info-mithuat-description {
        display: flex;
        padding: 20px;
        border: 1px solid #ddd;
        box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
        gap: 70px;
        position: absolute;
        z-index: 1;
        background-color: white;
        left: 0;
        right: 0;
        box-sizing: border-box;
        flex-wrap: nowrap;
    }


    #section-info-mithuat .info-mithuat-description .info-mithuat-description-first a {
        display: block;
        padding-right: 20px;
        width: 100%;
        margin-bottom: 20px;
        text-decoration: none;
        color: black;
    }

    #section-info-mithuat .info-mithuat-description .info-mithuat-description-first a:hover {
        color: #17479D;
    }

    #section-info-mithuat .info-mithuat-description .info-mithuat-description-second a {
        display: block;
        padding-right: 20px;
        width: 100%;
        margin-bottom: 15px;
        text-decoration: none;
        color: black;
    }

    #section-info-mithuat .info-mithuat-description .info-mithuat-description-second a:hover {
        color: #17479D;
    }


    #section-info-mithuat .info-mithuat-description .info-mithuat-description-img img {
        width: 100%;
        cursor: pointer;
    }

</style>
<div class="info-mithuat-header">
    <i class="fa-solid fa-palette"></i>
    <p class="info-mithuat-header-content"><a
            href="#">Danh
        mục</a></p>
    <i class="fa-solid fa-angle-down"></i>
</div>
<div class="hover-block">
    <div class="info-mithuat-description">
        <div class="info-mithuat-description-first">
            <c:forEach var="c" items="${categories}">
                <div class="category-item" data-image="${c.thumbnail}">
                    <a href="${pageContext.request.contextPath}/category?id=${c.id}">
                            ${c.categoryName}
                    </a>
                </div>
            </c:forEach>
        </div>
        <div class="info-mithuat-description-second">
        </div>
        <div class="info-mithuat-description-img">
            <c:if test="${not empty categories}">
                <img id="categoryPreview" src="${categories[0].thumbnail}" alt="Ảnh danh mục">
            </c:if>
        </div>
    </div>
</div>
<script>
    const catList = document.querySelectorAll(".category-item");
    const catImg = document.getElementById("categoryPreview");

    catList.forEach(function (item) {
        item.addEventListener("mouseenter", function () {
            catImg.src = item.getAttribute("data-image");
        });
    });
</script>

