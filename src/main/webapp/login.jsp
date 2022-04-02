<!DOCTYPE html>
<html lang="en">

<head>
    <meta charset="utf-8">

    <meta content="width=device-width, initial-scale=1.0" name="viewport">

    <title>LiteNMS | Login</title>

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

    <!-- Template Main CSS File -->
    <link href="ExternalStylingAndLibraries/assets/css/style.css" rel="stylesheet">

    <!-- =======================================================
    * Template Name: NiceAdmin - v2.2.2
    * Template URL: https://bootstrapmade.com/nice-admin-bootstrap-admin-html-template/
    * Author: BootstrapMade.com
    * License: https://bootstrapmade.com/license/
    ======================================================== -->
</head>

<body>

<main>

    <div class="container">

        <section class="section register min-vh-100 d-flex flex-column align-items-center justify-content-center py-4">

            <div class="container">

                <div class="row justify-content-center">

                    <div class="col-lg-5 col-md-6 d-flex flex-column align-items-center justify-content-center">

                        <div class="d-flex justify-content-center py-4">

                            <a href="index.html" class="logo d-flex align-items-center w-auto">

                                <img src="ExternalStylingAndLibraries/assets/img/logo.png" alt="">

                                <span class="d-none d-lg-block">LiteNMS</span>

                            </a>

                        </div><!-- End Logo -->

                        <div class="card mb-3">

                            <div class="card-body">

                                <div class="pt-4 pb-2">

                                    <h5 class="card-title text-center pb-0 fs-4">Sign In</h5>

                                    <p class="text-center small">Enter your username & password to Sign In</p>

                                </div>

                                <form class="row g-3 needs-validation" action="login" method="post" novalidate>

                                    <div class="col-12">

                                        <label for="username" class="form-label">Username</label>

                                        <div class="input-group has-validation">

                                            <input type="text" name="username" class="form-control" name="username" id="username" required autocomplete="off">

                                            <i class='input-group-text lg bx bx-user-circle bx-sm' ></i>

                                            <div class="invalid-feedback">Please enter your username.</div>

                                        </div>

                                    </div>

                                    <div class="col-12 mb-3">

                                        <label for="password" class="form-label">Password</label>

                                        <div class="input-group has-validation">

                                            <input type="password" name="password" class="form-control" name="password" id="password" required>

                                            <i class='input-group-text lg bx bx-lock bx-sm' ></i>

                                            <button type="button" id="watchPassword" style="padding: 0; border: grey;">

                                                <i class='input-group-text lg bi bi-eye-fill' style="font-size: x-large;"></i>

                                                <i class='input-group-text lg bi bi-eye-slash-fill' style="font-size: x-large; display: none;"></i>

                                            </button>

                                            <div class="invalid-feedback">Please enter your password!</div>

                                        </div>

                                    </div>

                                    <div class="col-12 mb-3">

                                        <button class="btn btn-primary w-100" type="submit" id="loginButton">Sign In</button>

                                    </div>

                                </form>

                            </div>

                        </div>

                        <div id="loginFailMessage" style="display:none">

                            <div class="alert alert-danger alert-dismissible fade show" role="alert">

                                Wrong username or password

                                <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>

                            </div>

                        </div>

                    </div>
                </div>

            </div>

        </section>

    </div>
</main><!-- End #main -->

<!-- Vendor JS Files -->
<script src="ExternalStylingAndLibraries/assets/vendor/apexcharts/apexcharts.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/chart.js/chart.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/echarts/echarts.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/quill/quill.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/simple-datatables/simple-datatables.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/tinymce/tinymce.min.js"></script>

<script src="ExternalStylingAndLibraries/assets/vendor/php-email-form/validate.js"></script>

<!-- Template Main JS File -->
<script src="ExternalStylingAndLibraries/assets/js/jquery-3.6.0.min.js"></script>

<script src="scripts/login.js"></script>

<script src="scripts/websocket.js"></script>

<script>
    $(document).ready(function ()
    {
        login.checkValidity();
    });
</script>

<script src="ExternalStylingAndLibraries/assets/js/main.js"></script>

</body>

</html>