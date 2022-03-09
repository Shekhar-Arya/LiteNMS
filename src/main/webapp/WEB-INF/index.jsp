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
        .dataTables_length{
            margin-top: 30px;
            margin-bottom: 20px;
        }
        .dataTables_filter{
            margin-top: 30px;
            margin-bottom: 20px;
        }
        .dataTables_info{
            margin-top: 30px;
            margin-bottom: 20px;
        }
        .dataTables_paginate{
            margin-top: 30px;
            margin-bottom: 20px;
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

        <li class="nav-item">
            <a class="nav-link " href="index.html">
                <i class="bi bi-search"></i>
                <span>Discovery</span>
            </a>
        </li><!-- End Monitor Discovery Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="index.html">
                <i class="bi bi-display"></i>
                <span>Monitor</span>
            </a>
        </li><!-- End Monitor Nav -->

        <li class="nav-item">
            <a class="nav-link collapsed" href="index.html">
                <i class="bi bi-grid"></i>
                <span>Dashboard</span>
            </a>
        </li><!-- End Dashboard Nav -->


    </ul>

</aside><!-- End Sidebar-->



<div id="main" class="main">
</div><!-- End #main -->
<%--Edit Form Start--%>
<button id="editForm" type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#editDiscoveryModal" style="display: none;">
</button>
<div class="modal fade" id="editDiscoveryModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title">Edit Device</h5>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <form class="row g-3 needs-validation" id="discoveryFormEdit"> <div class="col-md-12"> <label for="editid" class="form-label">Id</label>  <input type="text" class="form-control" id="editid" required="required" disabled/> </div> <div class="col-md-12"> <label for="editname" class="form-label">Name</label>  <input type="text" class="form-control" id="editname" required="required"/> </div> <div class="col-md-6"> <label for="editip" class="form-label">IP/Host</label> <input type="text" class="form-control" id="editip" required/></div> <div class="col-md-6"> <label for="edittype" class="form-label">Type</label> <select id="edittype" class="form-select" required disabled> <option selected>Ping</option> <option>SSH</option> </select> </div> <div class="col-md-6" id="usernameDiv" style="display: none"> <label for="editusername" class="form-label">Username</label> <input type="text" class="form-control" id="editusername"/> </div> <div class="col-md-6" id="passwordDiv" style="display: none"> <label for="editpassword" class="form-label">Password</label> <input id="editpassword" type="password" class="form-control"/></div> <div class="text-center"> <button type="submit" class="btn btn-primary">Submit</button> <button type="reset" class="btn btn-secondary">Reset</button> </div> </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
            </div>
        </div>
    </div>
</div><!-- End Basic Modal-->
<%--Edit Form Ends--%>

</div>
</div>




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

<!-- Template Main JS File -->
<script src="ExternalStylingAndLibraries/assets/js/main.js"></script>


<%--<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>--%>
<%--<script type="text/javascript" src="https://code.jquery.com/jquery-3.5.1.js"></script>--%>

<script type="text/javascript" src="https://cdn.datatables.net/1.11.5/js/jquery.dataTables.min.js"></script>
<script src="scripts/login.js"></script>
<script src="scripts/discovery.js"></script>
<script src="scripts/ajaxCalls.js"></script>
<script>
    $(document).ready(function (){
        login.logout();
        discovery.loadDiscoveryPage();
        discovery.addDevice();
        discovery.displaySSHFields();
        discovery.getDiscoveryDevices();
        discovery.deleteDiscoveryRow();
        discovery.getDiscoveryRow();
    });
</script>
</body>
</html>
