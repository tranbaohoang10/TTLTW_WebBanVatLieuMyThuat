<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Chi tiết sản phẩm</title>
    <link rel="stylesheet" href="./assets/css/style.css">
    <link rel="stylesheet"
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/7.0.1/css/all.min.css"
          integrity="sha512-2SwdPD6INVrV/lHTZbO2nodKhrnDdJK9/kg2XD1r9uGqPo1cUbujc+IYdlYdEErWNu69gVcYgdxlmVmzTWnetw=="
          crossorigin="anonymous" referrerpolicy="no-referrer"/>

</head>

<style>
    .container {
        width: 1200px;
        margin: auto;
    }

    .breadcrumb {
        background-color: #f0f4f8;
        padding: 1rem 2.5rem;
        font-size: 0.875rem;
    }

    .breadcrumb a {
        color: #17479d;
        text-decoration: none;
        transition: color 0.3s;
    }

    .breadcrumb a:hover {
        color: #0d2a5e;
        text-decoration: underline;
    }

    .breadcrumb span {
        color: #666;
        margin: 0 0.5rem;
    }

    /* ========== PRODUCT DETAIL STYLES ========== */
    .product-detail-container {
        max-width: 1400px;
        margin: 2rem auto;
        padding: 0 2.5rem;
    }

    .product-detail {
        display: grid;
        grid-template-columns: 1fr 1fr;
        gap: 3rem;
        background: white;
        padding: 2rem;
        border-radius: 12px;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    }

    /* Product Images */
    .product-images {
        display: flex;
        flex-direction: column;
        gap: 1rem;
        max-width: 500px;
        margin: 0 auto;
    }

    .product-main-image {
        position: relative;
        width: 100%;
        aspect-ratio: 1;
        border-radius: 12px;
        overflow: hidden;
        border: 2px solid #e5e7eb;
    }

    .product-main-image img {
        width: 100%;
        height: 100%;
        object-fit: contain;
        background: #fef2f2;
    }

    .discount-badge {
        position: absolute;
        top: 10px;
        right: 10px;
        background: linear-gradient(135deg, #E73838 0%, #c62828 100%);
        color: white;
        padding: 8px 12px;
        border-radius: 50%;
        font-weight: 700;
        font-size: 0.75rem;
        width: 70px;
        height: 70px;
        display: flex;
        flex-direction: column;
        align-items: center;
        justify-content: center;
        box-shadow: 0 4px 12px rgba(239, 68, 68, 0.4);
        line-height: 1.2;
        z-index: 10;
    }

    .discount-badge div:first-child {
        font-size: 1rem;
        font-weight: 600;
        margin-bottom: 2px;
    }

    .discount-badge div:last-child {
        font-size: 1.1rem;
        font-weight: 700;
    }

    .main-image-nav {
        position: absolute;
        top: 50%;
        transform: translateY(-50%);
        width: 40px;
        height: 40px;
        background: rgba(255, 255, 255, 0.9);
        border: 1px solid rgba(0, 0, 0, 0.1);
        border-radius: 50%;
        display: flex;
        align-items: center;
        justify-content: center;
        cursor: pointer;
        text-decoration: none;
        color: #374151;
        font-size: 1.125rem;
        font-weight: 600;
        transition: all 0.3s;
        z-index: 10;
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.15);
    }

    .main-image-nav:hover {
        background: white;
        color: #17479d;
        transform: translateY(-50%) scale(1.1);
        box-shadow: 0 4px 12px rgba(23, 71, 157, 0.3);
    }

    .main-image-prev {
        left: 15px;
    }

    .main-image-next {
        right: 15px;
    }

    /* Thumbnail Gallery */
    .thumbnail-gallery {
        display: flex;
        justify-content: center;
        position: relative;
        width: 100%;
        max-width: 360px;
        margin: 0 auto;
    }

    .thumbnail-container {
        display: flex;
        gap: 0.5rem;
        overflow-x: auto;
        scroll-behavior: smooth;
        scrollbar-width: none;
        -ms-overflow-style: none;
        scroll-snap-type: x mandatory;
    }

    .thumbnail-container::-webkit-scrollbar {
        display: none;
    }

    .thumbnail-item {
        flex: 0 0 100px;
        min-width: 100px;
        width: 100px;
        height: 100px;
        border: 2px solid #e5e7eb;
        border-radius: 8px;
        overflow: hidden;
        cursor: pointer;
        transition: all 0.3s;
        background: #fef2f2;
        scroll-snap-align: start;
    }

    .thumbnail-item:hover {
        border-color: #17479d;
        transform: scale(1.05);
        box-shadow: 0 4px 12px rgba(23, 71, 157, 0.3);
    }

    .thumbnail-item.active {
        border-color: #2659F3;
        border-width: 3px;
        box-shadow: 0 4px 12px rgba(38, 89, 243, 0.5);
    }

    .thumbnail-item img {
        width: 100%;
        height: 100%;
        object-fit: contain;
    }


    /* Product Info */
    .product-info {
        display: flex;
        flex-direction: column;
        gap: 1.5rem;
    }

    .product-title {
        font-size: 1.5rem;
        font-weight: 700;
        color: #1f2937;
        line-height: 1.3;
        margin: 0;
    }

    .product-meta {
        display: flex;
        flex-direction: column;
        gap: 0.5rem;
        padding: 1rem 0;
        border-bottom: 1px solid #e5e7eb;
    }

    .product-meta-item {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        font-size: 0.9375rem;
    }

    .product-meta-label {
        color: #6b7280;
        font-weight: 500;
    }

    .product-meta-value {
        color: #17479d;
        font-weight: 600;
    }

    .product-meta-value a {
        color: #17479d;
        text-decoration: none;
    }

    .product-meta-value a:hover {
        text-decoration: underline;
    }

    /* Price Section */
    .product-price-section {
        background: #f9fafb;
        padding: 1.5rem;
        border-radius: 8px;
        border: 1px solid #e5e7eb;
    }

    .product-price {
        display: flex;
        align-items: baseline;
        gap: 1rem;
        margin-bottom: 0.5rem;
    }

    .price-current {
        font-size: 2rem;
        font-weight: 700;
        color: #2659F3;
    }

    .price-original {
        font-size: 1.125rem;
        color: #9ca3af;
        text-decoration: line-through;
    }


    .variant-label {
        font-weight: 600;
        color: #374151;
        font-size: 1rem;
    }

    /* Quantity Selector */
    .quantity-section {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    .quantity-controls {
        display: flex;
        align-items: center;
        gap: 0.5rem;
        width: fit-content;
    }

    .quantity-btn {
        min-width: 35px;
        height: 35px;
        border: 1px solid #d1d5db;
        background: white;
        border-radius: 6px;
        cursor: pointer;
        font-size: 1.125rem;
        font-weight: 400;
        color: #374151;
        transition: all 0.2s;
        display: flex;
        align-items: center;
        justify-content: center;
        padding: 0;
    }

    .quantity-btn:hover {
        border-color: #9ca3af;
        background: #f9fafb;
    }

    .quantity-btn:active {
        background: #e5e7eb;
        transform: scale(0.95);
    }

    .quantity-input {

        height: 35px;
        border: 1px solid #d1d5db;
        border-radius: 6px;
        text-align: center;
        font-size: 1rem;
        font-weight: 500;
        outline: none;
        background: white;
        color: #1f2937;
        transition: all 0.2s;
    }

    .quantity-input:focus {
        border-color: #17479d;
        box-shadow: 0 0 0 3px rgba(23, 71, 157, 0.1);
    }

    /* Action Buttons */
    .product-actions {
        display: flex;
        gap: 1rem;
        margin-top: 1rem;
    }

    .btn {
        flex: 1;
        padding: 0.875rem 1.5rem;
        border: none;
        border-radius: 8px;
        font-size: 1rem;
        font-weight: 600;
        cursor: pointer;
        transition: all 0.3s;
        display: flex;
        align-items: center;
        justify-content: center;
        gap: 0.5rem;
    }

    .btn-add-cart {
        background: white;
        color: #2659F3;
        border: 2px solid #2659F3;
    }

    .btn-add-cart:hover {
        background: #eff6ff;
        transform: translateY(-2px);
        box-shadow: 0 4px 12px rgba(23, 71, 157, 0.2);
    }

    .btn-buy-now {
        background: linear-gradient(135deg, #2659F3 0%, #2659F3 100%);
        color: white;
        border: 2px solid transparent;
    }

    .btn-buy-now:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(23, 71, 157, 0.4);
    }

    /* Animation cho nút thêm vào giỏ */
    .btn-add-cart.adding {
        animation: addToCartPulse 0.6s ease;
    }

    @keyframes addToCartPulse {
        0% {
            transform: scale(1);
        }
        50% {
            transform: scale(0.95);
        }
        100% {
            transform: scale(1);
        }
    }


    .toast-notification i {
        font-size: 1.5rem;
        animation: checkmarkPop 0.6s ease;
    }

    @keyframes checkmarkPop {
        0% {
            transform: scale(0) rotate(0deg);
        }
        50% {
            transform: scale(1.2) rotate(180deg);
        }
        100% {
            transform: scale(1) rotate(360deg);
        }
    }

    .toast-notification .toast-content {
        display: flex;
        flex-direction: column;
        gap: 0.25rem;
    }

    .toast-notification .toast-title {
        font-weight: 600;
        font-size: 1rem;
    }

    .toast-notification .toast-message {
        font-size: 0.875rem;
        opacity: 0.9;
    }

    .toast-notification .toast-close {
        background: none;
        border: none;
        color: white;
        font-size: 1.25rem;
        cursor: pointer;
        padding: 0;
        margin-left: 0.5rem;
        opacity: 0.8;
        transition: opacity 0.2s;
    }

    .toast-notification .toast-close:hover {
        opacity: 1;
    }


    @keyframes cartShake {
        0%, 100% {
            transform: translateX(0);
        }
        25% {
            transform: translateX(-5px);
        }
        75% {
            transform: translateX(5px);
        }
    }

    /* Policy Section */
    .policy {
        max-width: 650px;
        margin-top: 1.5rem;
        padding: 1.5rem;
        background: #f8fafc;
        border-radius: 12px;
    }

    .policy_title {
        background: linear-gradient(135deg, #2659F3 0%, #1e40af 100%);
        padding: 0.75rem 1.5rem;
        border-radius: 8px;
        margin-bottom: 1rem;
    }

    .policy_title h2 {
        font-size: 1.125rem;
        font-weight: 600;
        color: white;
        margin: 0;
    }

    .policy_list {
        display: flex;
        flex-direction: column;
        gap: 1rem;
    }

    .policy_detail {
        display: flex;
        align-items: center;
        gap: 1rem;
        padding: 0.75rem;
        background: white;
        border-radius: 8px;
        transition: all 0.3s;
    }

    .policy_detail:hover {
        transform: translateX(4px);
        box-shadow: 0 2px 8px rgba(0, 0, 0, 0.08);
    }

    .policy_detail img {
        width: 48px;
        height: 48px;
        object-fit: contain;
        flex-shrink: 0;
    }

    .policy_detail p {
        font-size: 1rem;
        color: #475569;
        line-height: 1.5;
        margin: 0;
    }

    /* Product Content - Thông số kỹ thuật */
    .product_content {
        max-width: 650px;
        margin-top: 1.5rem;
        background: white;
        border-radius: 12px;
        padding: 1.5rem;
        border: 1px solid #e2e8f0;
    }

    .content_name {
        background: linear-gradient(135deg, #2659F3 0%, #1e40af 100%);
        padding: 0.75rem 1.5rem;
        border-radius: 8px;
        margin-bottom: 1rem;
    }

    .content_name h3 {
        font-size: 1.125rem;
        font-weight: 600;
        color: white;
        margin: 0;
    }

    .content_blog {
        padding: 0;
    }

    .content_blog-item {
        border-bottom: 1px solid #e2e8f0;
    }

    .content_blog-item:last-child {
        border-bottom: none;
    }

    .item-main {
        padding: 1.5rem;
    }

    .item-main ul {
        list-style: none;
        padding: 0;
        margin: 0;
    }

    .item-main li {
        display: grid;
        grid-template-columns: 200px 1fr;
        gap: 1rem;
        padding: 1rem 0;
        border-bottom: 1px solid #f1f5f9;
    }

    .item-main li:last-child {
        border-bottom: none;
    }

    .item-main h3 {
        font-size: 0.9375rem;
        font-weight: 600;
        color: #475569;
        margin: 0;
    }

    .item-main p {
        font-size: 0.9375rem;
        color: #1e293b;
        margin: 0;
        line-height: 1.6;
    }

    /* Product Rate Buttons */
    .product_rate {
        margin-top: 1.5rem;
        display: flex;
        gap: 1rem;
        padding: 1rem 0;
    }

    .product_rate .buy_now {
        flex: 1;
        padding: 0.875rem 1.5rem;
        border-radius: 8px;
        text-decoration: none;
        text-align: center;
        font-weight: 600;
        transition: all 0.3s;
        border: 2px solid #2659F3;
    }


    .btn_view {
        max-width: 650px;
        background: linear-gradient(135deg, #2659F3 0%, #1e40af 100%);
        color: white;
        border-color: transparent;
    }

    .btn_view:hover {
        transform: translateY(-2px);
        box-shadow: 0 8px 20px rgba(38, 89, 243, 0.4);
    }

    .product_rate .buy_now p {
        margin: 0;
        font-size: 1rem;
    }

    .link {
        display: flex;
        align-items: center;
        text-decoration: none;
        color: inherit;
    }

    .btn-buy-now {
        text-decoration: none;
        color: #fff;
    }

    /* Responsive Design */
    @media (max-width: 992px) {
        .product-detail {
            grid-template-columns: 1fr;
            gap: 2rem;
        }
    }

    @media (max-width: 768px) {
        .product-detail-container {
            padding: 0 1rem;
            margin: 1rem auto;
        }

        .product-detail {
            padding: 1rem;
        }

        .product-title {
            font-size: 1.25rem;
        }

        .price-current {
            font-size: 1.75rem;
        }

        .product-actions {
            flex-direction: column;
        }

        .thumbnail-gallery {
            max-width: 100%;
        }

        .thumbnail-item {
            flex: 0 0 90px;
            min-width: 90px;
            width: 90px;
            height: 90px;
        }


    }

    @media (max-width: 480px) {
        .breadcrumb {
            font-size: 0.75rem;
            padding: 0.75rem 1rem;
        }

        .product-title {
            font-size: 1.125rem;
        }

        .price-current {
            font-size: 1.5rem;
        }

        .thumbnail-gallery {
            max-width: 100%;
        }

        .thumbnail-item {
            flex: 0 0 80px;
            min-width: 80px;
            width: 80px;
            height: 80px;
        }

        .item-main li {
            grid-template-columns: 1fr;
            gap: 0.5rem;
        }

        .item-main h3 {
            font-weight: 700;
        }

        .policy_list {
            gap: 0.75rem;
        }

        .policy_detail {
            padding: 0.5rem;
        }

        .policy_detail img {
            width: 40px;
            height: 40px;
        }

        .product_rate {
            flex-direction: column;
        }
    }

    .section-but {
        margin-bottom: 100px;
        margin-top: 100px;

    }

    .section-but .container {
        padding: 15px;
        border-radius: 15px;
        box-shadow: rgba(149, 157, 165, 0.2) 0px 8px 24px;
        min-width: 1400px;
    }

    .section-but img {
        width: 100%;
        height: auto;
        cursor: pointer;
        border-radius: 15px;

    }

    .section-but .section-but-header {
        display: flex;
        justify-content: space-between;
        align-items: center;
    }

    .section-but .section-but-header h2 {
        font-size: 20px;
        color: #17479D;
    }

    .section-but .section-but-header .list-type a {
        display: inline-block;
        padding: 8px 20px;
        text-decoration: none;
        color: black;
        border: 1px solid #ddd;
        border-radius: 30px;
        margin-left: 5px;
    }

    .section-but .section-but-header .list-type a:nth-child(1) {
        text-decoration: none;
        color: #17479D;
        border: 1px solid #17479D;
    }

    .section-but .section-but-header .list-type a:last-child {

        margin-right: 3px;
    }

    .section-but .list-product {
        display: flex;
        gap: 20px;
        margin-bottom: 30px;
        flex-wrap: wrap;
    }

    .section-but .list-product .list-product-list1 {
        border-radius: 15px;
        background-color: #F8FAFF;
        border: 1px solid #D7E5FF;
        width: calc(20% - 18px);
        cursor: pointer;
    }

    .section-but .list-product .list-product-list1 img {
        border-radius: 15px;
        width: 100%;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials {
        display: flex;
        align-items: center;
        gap: 10px;
        margin-top: 10px;

    }

    .section-but .list-product .list-product-list1 .list-product-list1-content {
        padding: 0 15px 15px;
        text-align: center;
    }

    /* chu y */
    .section-but .list-product .list-product-list1 .list-product-list1-content .content {
        width: 200px;
        white-space: nowrap;
        /* không cho xuống dòng */
        overflow: hidden;
        /* ẩn phần bị tràn */
        text-overflow: ellipsis;
        /* thêm dấu … */
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-1 {
        width: 60px;
        border-radius: 10px;
        text-align: center;
        line-height: 30px;
        /* height: 30px; */
        /* padding: 5px; */
        background-color: #FFE0E2;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-1 i {
        color: #ED1C24;
        font-size: 10px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-1 span {
        /* display: inline-block; */
        color: #ED1C24;
        font-size: 12px;
        font-weight: 600;
        margin-left: 5px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-2 {
        width: 100px;
        border-radius: 10px;
        text-align: center;
        line-height: 30px;
        /* height: 30px; */
        background-color: #E0EBFF;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-2 i {
        color: #268EF8;
        font-size: 10px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content-socials .list-product-list1-content-socials-2 span {
        color: #268EF8;
        font-size: 12px;
        font-weight: 600;
        margin-left: 5px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description p {
        font-size: 14px;
        color: black;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .star i {
        color: #F3C63F
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .star span {
        color: #F3C63F;
        margin-left: 10px;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .price-product-after {
        color: #268EF8;
        font-weight: 600;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .price-product-before {
        color: #ccc;
        font-weight: 600;
        text-decoration: line-through;
        display: inline-block;
        margin: 0;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .price-product-discount {
        color: #ED1C24;
        font-weight: 600;
        margin-left: 5px;
        width: 60px;
        border-radius: 20px;
        text-align: center;
        line-height: 30px;
        /* height: 30px; */
        /* padding: 5px; */
        display: inline-block;
        background-color: #FFE0E2;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button {
        margin-top: 15px;
        text-align: center;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button .btn-xemchitiet {
        min-width: 64%;
        padding: 10px 0;
        background-color: transparent;
        border: 1px solid #268EF8;
        border-radius: 10px;
        color: white;
        cursor: pointer;

    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button .btn-xemchitiet {
        color: #268EF8;
    }

    .section-but .list-product .list-product-list1 .list-product-list1-content .list-product-list1-content-description .button .btn-xemchitiet:hover {
        background-color: #268EF8;
        color: white;
    }

    .section-but .section-but-button {
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .section-but .section-but-button .button {
        background-color: #17479D;
        border: none;
        padding: 15px 15px;
        border-radius: 20px;
        color: white;
        cursor: pointer;
        font-size: 16px;
    }

    a {
        text-decoration: none;
    }

    .section-but .section-but-button .button:hover {
        opacity: 0.9;
    }
    .btn-disabled {
        opacity: 0.55;
        cursor: not-allowed;
        pointer-events: none; /* chặn click link */
    }
    button:disabled {
        opacity: 0.55;
        cursor: not-allowed;
    }

</style>

<body>
<%@ include file="Header.jsp" %>

<nav>

    <div class="breadcrumb">
        <a href="index.html">Trang chủ</a>
        <span>/</span>
        <a href="ti">Bút Lông Màu</a>
        <span>/</span>
        <span>Bút Lông Thiên Long 48/60 Màu Fiber Pen Washable -
                Phiên
                bản Demon Slayer</span>
    </div>


    <div class="product-detail-container">
        <div class="product-detail">


            <div class="product-images">

                <div class="product-main-image">
                    <c:choose>
                        <c:when test="${not empty subimagesList}">
                            <c:set var="mainImgUrl" value="${subimagesList[0].image}" />
                            <c:if test="${not empty mainImgUrl and not fn:startsWith(mainImgUrl,'http') and not fn:startsWith(mainImgUrl, pageContext.request.contextPath)}">
                                <c:choose>
                                    <c:when test="${fn:startsWith(mainImgUrl,'/')}">
                                        <c:set var="mainImgUrl" value="${pageContext.request.contextPath}${mainImgUrl}" />
                                    </c:when>
                                    <c:otherwise>
                                        <c:set var="mainImgUrl" value="${pageContext.request.contextPath}/${mainImgUrl}" />
                                    </c:otherwise>
                                </c:choose>
                            </c:if>

                            <img src="${mainImgUrl}"
                                 alt="${product.name}"
                                 id="mainProductImage">
                        </c:when>

                        <c:otherwise>
                            <img src="./assets/images/logo/list1-1.jpg"
                                 alt="${product.name}"
                                 id="mainProductImage">
                        </c:otherwise>
                    </c:choose>

                    <div class="discount-badge">
                        <div>Tiết kiệm</div>
                        <div style="font-size: 1rem;">${product.discountDefault}%</div>
                    </div>

                    <a href="javascript:void(0)" id="prevImageBtn"
                       class="main-image-nav main-image-prev">&lt;</a>
                    <a href="javascript:void(0)" id="nextImageBtn"
                       class="main-image-nav main-image-next">&gt;</a>
                </div>


                <div class="thumbnail-gallery">
                    <div class="thumbnail-container" id="thumbnailContainer">
                        <c:choose>
                            <c:when test="${not empty subimagesList}">
                                <c:forEach var="img" items="${subimagesList}" varStatus="st">
                                    <div class="thumbnail-item ${st.first ? 'active' : ''}"
                                         data-index="${st.index}">
                                        <c:set var="subImgUrl" value="${img.image}" />
                                        <c:if test="${not empty subImgUrl and not fn:startsWith(subImgUrl,'http') and not fn:startsWith(subImgUrl, pageContext.request.contextPath)}">
                                            <c:choose>
                                                <c:when test="${fn:startsWith(subImgUrl,'/')}">
                                                    <c:set var="subImgUrl" value="${pageContext.request.contextPath}${subImgUrl}" />
                                                </c:when>
                                                <c:otherwise>
                                                    <c:set var="subImgUrl" value="${pageContext.request.contextPath}/${subImgUrl}" />
                                                </c:otherwise>
                                            </c:choose>
                                        </c:if>

                                        <img src="${subImgUrl}"
                                             alt="Ảnh phụ ${st.index + 1}">

                                    </div>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <div class="thumbnail-item active" data-index="0">
                                    <img src="./assets/images/logo/list1-1.jpg" alt="Ảnh 1">
                                </div>
                                <div class="thumbnail-item" data-index="1">
                                    <img src="./assets/images/logo/list1-1.jpg" alt="Ảnh 2">
                                </div>
                                <div class="thumbnail-item" data-index="2">
                                    <img src="./assets/images/logo/list1-1-cont.png" alt="Ảnh 3">
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>


            <div class="product-info">
                <h1 class="product-title">${product.name}</h1>

                <div class="product-meta">
                    <div class="product-meta-item">
                        <span class="product-meta-label">Thương hiệu:</span>
                        <span class="product-meta-value">
                        <a href="#">${product.brand}</a>
                    </span>
                    </div>
                    <div class="product-meta-item">
                        <span class="product-meta-label">Tình trạng:</span>
                        <span class="product-meta-value"
                              style="color: ${outOfStock ? '#ef4444' : '#10b981'};">
                          <c:choose>
                              <c:when test="${outOfStock}">Hết hàng</c:when>
                              <c:otherwise>Còn hàng</c:otherwise>
                          </c:choose>
                        </span>
                    </div>
                    <div class="product-meta-item">
                        <span class="product-meta-label">Mã sản phẩm:</span>
                        <span class="product-meta-value">${product.id}</span>
                    </div>
                </div>

                <div class="product-price-section">
                    <div class="product-price">
                    <span class="price-current">
                        <fmt:formatNumber value="${product.priceAfterDiscount}" type="number"/>₫
                    </span>
                        <span class="price-original">
                            <fmt:formatNumber value="${product.price}" type="number"/>₫
                    </span>
                    </div>
                </div>

                <form action="${pageContext.request.contextPath}/AddToCart?action=add"
                      method="post" id="addToCartForm">


                    <input type="hidden" name="productId" value="${product.id}"/>

                    <div class="quantity-section">
                        <div class="variant-label">Số lượng:</div>
                        <div class="quantity-controls">
                            <button type="button" class="quantity-btn"
                                    onclick="decreaseQuantity()"
                            ${outOfStock ? "disabled" : ""}>-</button>

                            <input type="number" class="quantity-input"
                                   id="quantity" name="quantity"
                                   value="1" min="1"
                                   max="${product.quantityStock}"
                            ${outOfStock ? "disabled" : ""}>
                            <button type="button" class="quantity-btn"
                                    onclick="increaseQuantity()"
                            ${outOfStock ? "disabled" : ""}>+</button>
                        </div>
                    </div>

                    <div class="product-actions">
                        <button type="submit" class="btn btn-add-cart" id="addToCartBtn"
                        ${outOfStock ? "disabled" : ""}>
                            <c:if test="${outOfStock}">
                                <i class="fa-solid fa-ban"></i>
                            </c:if>
                            <c:if test="${not outOfStock}">
                                <i class="fa-solid fa-cart-plus"></i>
                            </c:if>
                            THÊM VÀO GIỎ
                        </button>

                        <a href="${outOfStock ? '#' : pageContext.request.contextPath.concat('/checkout')}"
                           class="btn btn-buy-now link ${outOfStock ? 'btn-disabled' : ''}"
                        ${outOfStock ? 'aria-disabled="true"' : ''}>
                            <c:if test="${outOfStock}">
                                <i class="fa-solid fa-ban"></i>
                            </c:if>
                            MUA NGAY
                        </a>
                    </div>
                </form>
            </div>

        </div>
    </div>

    <div class="section-but">
        <div class="container">
            <div class="section-but-header">
                <h2>SẢN PHẨM CÙNG LOẠI</h2>
            </div>

            <div class="section-but-content">
                <div class="list-product">
                    <c:forEach var="p" items="${relatedProducts}">
                        <div class="list-product-list1">
                            <a href="${pageContext.request.contextPath}/DetailsProductController?id=${p.id}">
                                <c:set var="relThumbUrl" value="${p.thumbnail}" />
                                <c:if test="${not empty relThumbUrl and not fn:startsWith(relThumbUrl,'http') and not fn:startsWith(relThumbUrl, pageContext.request.contextPath)}">
                                    <c:choose>
                                        <c:when test="${fn:startsWith(relThumbUrl,'/')}">
                                            <c:set var="relThumbUrl" value="${pageContext.request.contextPath}${relThumbUrl}" />
                                        </c:when>
                                        <c:otherwise>
                                            <c:set var="relThumbUrl" value="${pageContext.request.contextPath}/${relThumbUrl}" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:if>

                                <img src="${relThumbUrl}" alt="${p.name}">
                                <div class="list-product-list1-content">
                                    <div class="list-product-list1-content-socials">
                                        <%@ include file="BadgeType.jsp" %>

                                        <div class="list-product-list1-content-socials-2">
                                            <i class="fa-solid fa-arrow-up-right-dots"></i>
                                            <span>Đã bán ${p.soldQuantity}</span>
                                        </div>
                                    </div>
                                    <div class="list-product-list1-content-description">
                                        <p class="content">${p.name}</p>

                                        <h2 class="price-product-after">
                                            <fmt:formatNumber value="${p.priceAfterDiscount}" type="number"/>₫

                                        </h2>
                                        <p class="price-product-before">
                                            <fmt:formatNumber value="${p.price}" type="number"/>₫
                                        </p>
                                        <span class="price-product-discount">
                                        -${p.discountDefault}%
                                    </span>

                                        <div class="button">
                                            <button class="btn-xemchitiet">
                                                <i class="fa-solid fa-eye"></i>
                                                Xem chi tiết
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </a>
                        </div>
                    </c:forEach>

                    <c:if test="${empty relatedProducts}">
                        <p>Không có sản phẩm cùng loại.</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="policy alpha bg-while">
        <div class="policy_title">
            <h2>Thiên Long xin cam kết</h2>
        </div>
        <div class="policy_list">
            <div class="policy_detail">
                <img

                        src="https://cdnv2.tgdd.vn/pim/cdn/images/202411/icon%20bao%20hanh173451.png"
                        alt>
                <p>Đảm bảo nguồn gốc xuất xứ rõ ràng, chất
                    liệu
                    an toàn, bền đẹp.</p>
            </div>
            <div class="policy_detail">
                <img
                        src="https://cdnv2.tgdd.vn/pim/cdn/images/202410/icon%20lap%20dat140848.png"
                        alt>
                <p>Giá niêm yết & minh bạch phí Không phụ
                    phí
                    ẩn</p>
            </div>
            <div class="policy_detail">
                <img
                        src="https://cdnv2.tgdd.vn/pim/cdn/images/202410/Exchange150303.png"
                        alt>
                <p>Quy trình đổi trả nhanh chóng, minh
                    bạch.</p>
            </div>

        </div>
    </div>
    <div class="product_content alpha bg-while">
        <div class="content_name">
            <h3>Thông số kĩ thuật</h3>
        </div>

        <div class="content_blog" id="content_blog">
            <div class="content_blog-item">
                <div class="item-main">
                    <c:if test="${not empty specificationList}">
                        <c:set var="spec" value="${specificationList[0]}"/>
                        <ul>
                            <li>
                                <h3>Kích thước bút:</h3>
                                <p>${spec.size}</p>
                            </li>
                            <li>
                                <h3>Tiêu chuẩn :</h3>
                                <p>${spec.standard}</p>
                            </li>
                            <li>
                                <h3>Sản xuất :</h3>
                                <p>${spec.madeIn}</p>
                            </li>
                            <li>
                                <h3>Khuyến cáo :</h3>
                                <p>${spec.warning}</p>
                            </li>
                        </ul>
                    </c:if>

                    <c:if test="${empty specificationList}">
                        <p>Chưa có thông số kỹ thuật cho sản phẩm này.</p>
                    </c:if>
                </div>
            </div>
        </div>
    </div>
    <div class="product_rate alpha bg-while  magin-bottom">

        <a class="btn_view buy_now" href="${pageContext.request.contextPath}/Product_ReviewsController?id=${product.id}">
            <p>Xem đánh giá</p>
        </a>
    </div>
    </div>
    </div>
</nav>
<%@ include file="Footer.jsp" %>

<!-- su kien nut tang giam so luong -->
<script>
    function increaseQuantity() {
        const input = document.getElementById("quantity");
        const value = parseInt(input.value) || 1;
        const max = parseInt(input.max) || 999999;
        if (value < max) input.value = value + 1;
    }

    function decreaseQuantity() {
        const input = document.getElementById("quantity");
        let value = parseInt(input.value);
        const min = parseInt(input.min) || 1;
        if (!isNaN(value) && value > min) {
            input.value = value - 1;
        } else {
            input.value = min;
        }
    }

    const addToCartForm = document.getElementById('addToCartForm');
    addToCartBtn = document.getElementById('addToCartBtn');
    
    addToCartForm.addEventListener('submit', function (e) {
        e.preventDefault(); // Ngăn form submit mặc định
        
        // animation cho nút
        addToCartBtn.classList.add('adding');
        addToCartBtn.disabled = true;

        // Lấy số lượng user chọn
        const qtyInput = document.getElementById('quantity');
        const qty = parseInt(qtyInput.value) || 1;
        const productId = document.querySelector('input[name="productId"]').value;

        // Gửi AJAX request
        fetch('${pageContext.request.contextPath}/AddToCart?action=add', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            },
            body: 'productId=' + productId + '&quantity=' + qty
        })
        .then(response => response.text())
        .then(data => {
            // Cập nhật số lượng giỏ hàng trên header
            updateCartCount(qty);
            
            // Hiển thị thông báo thành công
            showToastNotification();
            
            // Reset animation
            setTimeout(() => {
                addToCartBtn.classList.remove('adding');
                addToCartBtn.disabled = false;
            }, 600);
        })
        .catch(error => {
            console.error('Error:', error);
            addToCartBtn.classList.remove('adding');
            addToCartBtn.disabled = false;
            alert('Có lỗi xảy ra khi thêm sản phẩm vào giỏ hàng');
        });
    });

    function updateCartCount(addedQty) {
        const cartIcon = document.getElementById('cartIcon');
        if (cartIcon) {
            // Lấy số lượng hiện tại từ badge
            const currentCount = parseInt(cartIcon.getAttribute('data-count') || 0);
            const newCount = currentCount + addedQty;
            
            // Cập nhật badge
            cartIcon.setAttribute('data-count', newCount);
            
            // Animation
            cartIcon.classList.add('cart-updated');
            setTimeout(() => {
                cartIcon.classList.remove('cart-updated');
            }, 600);
        }
    }

    function showToastNotification() {
        const toast = document.createElement('div');
        toast.className = 'toast-notification';

        const icon = document.createElement('i');
        icon.className = 'fa-solid fa-circle-check';
        toast.appendChild(icon);

        const toastContent = document.createElement('div');
        toastContent.className = 'toast-content';

        const toastTitle = document.createElement('div');
        toastTitle.className = 'toast-title';
        toastTitle.textContent = 'Thành công!';
        toastContent.appendChild(toastTitle);

        const toastMessage = document.createElement('div');
        toastMessage.className = 'toast-message';
        toastMessage.textContent = 'Đã thêm sản phẩm vào giỏ hàng';
        toastContent.appendChild(toastMessage);

        toast.appendChild(toastContent);

        const closeBtn = document.createElement('button');
        closeBtn.className = 'toast-close';
        closeBtn.textContent = '×';
        toast.appendChild(closeBtn);

        document.body.appendChild(toast);


        setTimeout(() => {
            toast.classList.add('show');
        }, 100);


        closeBtn.addEventListener('click', () => {
            hideToast(toast);
        });

        setTimeout(() => {
            hideToast(toast);
        }, 5000);
    }

    function hideToast(toast) {
        toast.classList.remove('show');
        setTimeout(() => {
            if (toast.parentNode) {
                toast.parentNode.removeChild(toast);
            }
        }, 400);
    }

    // su kien chuyen anh san pham
    document.addEventListener('DOMContentLoaded', function () {
        const mainImage = document.getElementById('mainProductImage');
        const thumbnails = document.querySelectorAll('.thumbnail-item');
        const prevBtn = document.getElementById('prevImageBtn');
        const nextBtn = document.getElementById('nextImageBtn');
        let currentIndex = 0;

        // Ham cap nhat anh chinh va border thumbnail
        function updateImage(index) {

            thumbnails.forEach(thumb => thumb.classList.remove('active'));

            thumbnails[index].classList.add('active');

            const imgSrc = thumbnails[index].querySelector('img').src;
            mainImage.src = imgSrc;

            currentIndex = index;
        }

        // Xu ly click vao thumbnail
        thumbnails.forEach((thumb, index) => {
            thumb.addEventListener('click', function () {
                updateImage(index);
            });
        });

        // Xu ly nut Previous
        if (prevBtn) {
            prevBtn.addEventListener('click', function (e) {
                e.preventDefault();
                currentIndex = (currentIndex - 1 + thumbnails.length) % thumbnails.length;
                updateImage(currentIndex);
            });
        }

        // Xu ly nut Next
        if (nextBtn) {
            nextBtn.addEventListener('click', function (e) {
                e.preventDefault();
                currentIndex = (currentIndex + 1) % thumbnails.length;
                updateImage(currentIndex);
            });
        }
    });
</script>

</body>

</html>