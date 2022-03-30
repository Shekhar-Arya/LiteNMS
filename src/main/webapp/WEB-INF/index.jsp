<%--
  Created by IntelliJ IDEA.
  User: shekhar
  Date: 03/03/22
  Time: 12:28 PM
  To change this template use File | Settings | File Templates.
--%>
<!DOCTYPE html>

<html lang="en">

<head>

    <meta charset = "UTF-8" />

    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>LiteNMS | Monitor</title>

    <meta content="" name="description">

    <meta content="" name="keywords">

    <!-- Favicons -->
    <link href="ExternalStylingAndLibraries/assets/img/favicon.png" rel="icon">

    <link href="ExternalStylingAndLibraries/assets/img/apple-touch-icon.png" rel="apple-touch-icon">

    <!-- Google Fonts -->
    <link href="https://fonts.gstatic.com" rel="preconnect">

    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,300i,400,400i,600,600i,700,700i|Nunito:300,300i,400,400i,600,600i,700,700i|Poppins:300,300i,400,400i,500,500i,600,600i,700,700i" rel="stylesheet">

    <!-- Vendor CSS Files -->
    <link href="ExternalStylingAndLibraries/assets/vendor/bootstrap/css/bootstrap.min.css" rel="stylesheet">

    <link href="ExternalStylingAndLibraries/assets/vendor/bootstrap-icons/bootstrap-icons.css" rel="stylesheet">

    <link href="ExternalStylingAndLibraries/assets/vendor/boxicons/css/boxicons.min.css" rel="stylesheet">

    <link href="ExternalStylingAndLibraries/assets/vendor/quill/quill.snow.css" rel="stylesheet">

    <link href="ExternalStylingAndLibraries/assets/vendor/quill/quill.bubble.css" rel="stylesheet">

    <link href="ExternalStylingAndLibraries/assets/vendor/remixicon/remixicon.css" rel="stylesheet">

    <link href="ExternalStylingAndLibraries/assets/vendor/simple-datatables/style.css" rel="stylesheet">

    <link rel="stylesheet" type="text/css" href="https://cdn.datatables.net/1.11.5/css/jquery.dataTables.min.css"/>

    <!-- Template Main CSS File -->
    <link href="ExternalStylingAndLibraries/assets/css/style.css" rel="stylesheet">

    <style>

        .dataTables_length
        {
            margin-top: 30px;

            margin-bottom: 20px;
        }

        .dataTables_filter
        {
            margin-top: 30px;

            margin-bottom: 20px;
        }

        .dataTables_info
        {
            margin-top: 30px;

            margin-bottom: 20px;
        }

        .dataTables_paginate
        {
            margin-top: 30px;

            margin-bottom: 20px;
        }


        .loading
        {
            display: block;

            position: absolute;

            top:0;

            left: 0;

            z-index: 100;

            width: 100vw;

            height: 100vh;

            background-color: rgba(0, 0, 0, 0.5);
        }

        .headerLoading
        {
            z-index: 100;
        }
        .sidebarLoading
        {
            z-index: 100;
        }

        .good
        {
            color: white;

            background-color: rgb(75, 192, 192);

            height: 300px;

            font-size: large;

            font-weight: bold;

            border-radius: 10px;
        }

        .bad
        {
            color: white;

            background-color: rgb(255, 99, 132);

            height: 300px;

            font-size: large;

            font-weight: bold;

            border-radius: 10px;
        }
    </style>

</head>

<body>

<%
    response.setHeader("Cache-Control","no-cache, no-store, must-revalidate"); //HTTP 1.1

    response.setHeader("Pragma","no-cache"); // HTTP 1.0

    response.setHeader("Expires","0"); // Proxies
%>

<!-- ======= Header ======= -->
<header id="header" class="header fixed-top d-flex align-items-center">

    <div class="d-flex align-items-center justify-content-between ms-3">

        <a class="logo d-flex align-items-center" style="width: 150px;">

            <img src="ExternalStylingAndLibraries/assets/img/logo.png" alt="">

            <span class="d-lg-block">LiteNMS</span>

        </a>

        <i class="bi bi-list toggle-sidebar-btn"></i>

    </div><!-- End Logo -->

    <nav class="header-nav ms-auto">

        <ul class="d-flex align-items-center">

            <li class="nav-item dropdown pe-3">

                <a class="nav-link nav-profile d-flex align-items-center pe-0 me-4" href="#" data-bs-toggle="dropdown">

                <span class="d-md-block dropdown-toggle" style="font-size: medium;">Admin</span>

                </a><!-- End Profile Iamge Icon -->

                <ul class="dropdown-menu dropdown-menu-end dropdown-menu-arrow profile">

                    <li class="dropdown-header">

                        <h6>Admin</h6>

                        <span>Network Admin</span>

                    </li>

                    <li>

                        <hr class="dropdown-divider">

                    </li>

                    <li>

                        <a class="dropdown-item d-flex align-items-center" href="logout">

                            <i class="bi bi-box-arrow-right"></i>

                            <span>Sign Out</span>

                        </a>

                    </li>

                </ul><!-- End Profile Dropdown Items -->

            </li><!-- End Profile Nav -->

        </ul>

    </nav><!-- End Icons Navigation -->

</header><!-- End Header -->

<!-- ======= Sidebar ======= -->
<aside id="sidebar" class="sidebar">

    <ul class="sidebar-nav" id="sidebar-nav">

        <li class="nav-item" id="discovery" style="cursor: pointer">

            <a class="nav-link ">

                <i class="bi bi-search"></i>

                <span>Discovery</span>

            </a>

        </li><!-- End Monitor Discovery Nav -->

        <li class="nav-item"  id="monitor" style="cursor: pointer">

            <a class="nav-link collapsed">

                <i class="bi bi-display"></i>

                <span>Monitor</span>

            </a>

        </li><!-- End Monitor Nav -->

        <li class="nav-item" id="dashboard" style="cursor: pointer">
            <a class="nav-link collapsed">
                <i class="bi bi-grid"></i>
                <span>Dashboard</span>
            </a>
        </li><!-- End Dashboard Nav -->

    </ul>

</aside><!-- End Sidebar-->

<div id="main" class="main">

</div><!-- End #main -->

<!-- Vendor JS Files -->
<script src="ExternalStylingAndLibraries/assets/js/jquery-3.6.0.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/apexcharts/apexcharts.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/chart.js/chart.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/echarts/echarts.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/quill/quill.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/simple-datatables/simple-datatables.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/tinymce/tinymce.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/php-email-form/validate.js"></script>

<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

<!-- Template Main JS File -->
<script src="ExternalStylingAndLibraries/assets/js/main.js"></script>

<script type="text/javascript" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>

<script src="scripts/login.js"></script>

<script src="scripts/discovery.js"></script>

<script src="scripts/monitor.js"></script>

<script src="scripts/dashboard.js"></script>

<script src="scripts/ajaxCalls.js"></script>

<script>

    $(document).ready(function (){

        login.logout();

        discovery.loadDiscoveryPage();

        monitor.loadMonitorData();

        dashboard.loadDashboardPage();

        if(sessionStorage.getItem("page")==undefined)
        {
            $("#discovery").click();
        }
        else
        {
            $(sessionStorage.getItem("page")).click();
        }
    });

</script>

</body>

</html>
