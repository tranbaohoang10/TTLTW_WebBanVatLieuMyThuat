
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<style>
    #section-main .section-main-filter {
        display: flex;
        flex-direction: column;
        flex-wrap: wrap;
        width: 15%;
        padding-left: 15px;
        padding-top: 30px;
        padding-bottom: 30px;
        position: sticky;
        top: 20px;
        height: fit-content;
    }

    #section-main .section-main-filter .header {
        color: #5980f6;
        margin-top: 0;
    }

    #section-main .section-main-filter input {
        display: inline-block;
        margin-right: 10px;
    }

    #section-main .section-main-filter-loaisanpham {
        border-bottom: 1px solid #ddd;
    }

    #section-main .section-main-filter-loaisanpham .list label {
        display: inline-block;
        cursor: pointer;
        margin-bottom: 10px;
    }

    #section-main .section-main-filter-loaisanpham .list input {
        display: inline-block;
        cursor: pointer;
    }

    #section-main .section-main-filter-loaisanpham .xemthem {
        margin-left: 30px;
        cursor: pointer;
        margin-bottom: 15px;
    }

    #section-main .section-main-filter-mucgia {
        border-bottom: 1px solid #ddd;
    }

    #section-main .section-main-filter-mucgia .list label {
        display: inline-block;
        margin-bottom: 10px;
        cursor: pointer;
        font-size: 14px;
    }

    #section-main .section-main-filter-mucgia .list input {
        display: inline-block;
        cursor: pointer;
    }



    #section-main .section-main-filter-tieuchi {
        border-bottom: 1px solid #ddd;
    }

    #section-main .section-main-filter-tieuchi .list label {
        display: inline-block;
        margin-bottom: 10px;
        cursor: pointer;
        font-size: 14px;
    }

    #section-main .section-main-filter-tieuchi .list input {
        display: inline-block;
        cursor: pointer;
    }
    #section-main .section-main-filter-mucgia .list label,
    #section-main .section-main-filter-tieuchi .list label {
        white-space: nowrap;
    }


</style>
<div class="section-main-filter">
    <form action="${pageContext.request.contextPath}/category" method="get" id="filterForm">
        <input type="hidden" name="categoryId" value="${category.id}"/>

        <!-- MỨC GIÁ -->
        <div class="section-main-filter-mucgia">
            <h3 class="header">Mức giá</h3>

            <div class="list">
                <label>
                    <input type="radio" name="priceRange" value="1"
                           <c:if test="${param.priceRange == '1'}">checked</c:if> />
                    Giá dưới 100.000đ
                </label><br>

                <label>
                    <input type="radio" name="priceRange" value="2"
                           <c:if test="${param.priceRange == '2'}">checked</c:if> />
                    100.000đ - 300.000đ
                </label><br>

                <label>
                    <input type="radio" name="priceRange" value="3"
                           <c:if test="${param.priceRange == '3'}">checked</c:if> />
                    300.000đ - 500.000đ
                </label><br>

                <label>
                    <input type="radio" name="priceRange" value="4"
                           <c:if test="${param.priceRange == '4'}">checked</c:if> />
                    500.000đ - 700.000đ
                </label><br>

                <label>
                    <input type="radio" name="priceRange" value="5"
                           <c:if test="${param.priceRange == '5'}">checked</c:if> />
                    700.000đ - 1.000.000đ
                </label><br>

                <label>
                    <input type="radio" name="priceRange" value="6"
                           <c:if test="${param.priceRange == '6'}">checked</c:if> />
                    Giá trên 1.000.000đ
                </label><br>
            </div>
        </div>

        <!-- TIÊU CHÍ -->
        <div class="section-main-filter-tieuchi">
            <h3 class="header">Tiêu chí</h3>

            <div class="list">
                <label>
                    <input type="radio" name="sort" value="priceDesc"
                           <c:if test="${param.sort == 'priceDesc'}">checked</c:if> />
                    Giá cao nhất
                </label><br>

                <label>
                    <input type="radio" name="sort" value="priceAsc"
                           <c:if test="${param.sort == 'priceAsc'}">checked</c:if> />
                    Giá thấp nhất
                </label><br>

                <label>
                    <input type="radio" name="sort" value="newest"
                           <c:if test="${param.sort == 'newest'}">checked</c:if> />
                    Sản phẩm mới
                </label><br>
            </div>
        </div>

    </form>
</div>
<script>
    var listRadio = document.querySelectorAll('#filterForm input[type="radio"]');
    for (var i = 0; i < listRadio.length; i++) {
        listRadio[i].onclick = function () {
            document.getElementById("filterForm").submit();
        }
    }
</script>

