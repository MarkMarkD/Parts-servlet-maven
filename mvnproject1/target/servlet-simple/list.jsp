<%@ page import="java.util.List" %>
<%@ page import="ru.transasia.mvnproject1.entities.Part" %>
<%@ page import="ru.transasia.mvnproject1.servlets.ListServlet" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Locale" %>
<%@ page import="ru.transasia.mvnproject1.entities.FilterValues" %><%--
  Created by IntelliJ IDEA.
  User: Дмитрий
  Date: 23.02.2018
  Time: 18:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>List of parts</title>
    <style type="text/css">
        table {
            border-collapse: collapse;
            border: 3px solid lightblue;
            margin: 0 auto;
            height: auto;
            width: 70%;
        }

        th {
            background-color: lightblue;
            border-bottom: 2px solid lightgray;
            text-align: left;
        }

        td:nth-child(1) {
            width: 200px;
        }

        tr {
            border-bottom: 1px solid lightgray;
        }

        td {
            height: 25px;
        }

        button {
            background-color: lightblue;

        }

        #filterButton {
            background-color: lightblue;

        }

        .applyFilter {
            text-align: center;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .outputMessage {
            text-align: center;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        tr,
        td,
        th {
            border: 1px solid lightgray;
            padding-left: 7px;
        }

        .PnumRes {
            width: 10%;
        }

        .PnameRes {
            width: 40%;
        }

        .VendorRes {
            width: 15%;
        }

        .QtyRes {
            width: 5%;
            text-align: center;
        }

        .ShpdRes {
            width: 15%;
        }

        .RcvdRes {
            width: 15%;
        }

    </style>
</head>
<body>
<div>

</div>
    <!-- filter table -->
    <table border="1" width = 40% cellspacing="0" cellpadding="3" >
        <%
            String pname = (String) request.getAttribute("pname");
            String pnumber = (String) request.getAttribute("pnumber");
            String vendor = (String) request.getAttribute("vendor");
            String qty = (String) request.getAttribute("qty");
            String shippedAfter = (String) request.getAttribute("shippedAfter");
            String shippedBefore = (String) request.getAttribute("shippedBefore");
            String receivedAfter = (String) request.getAttribute("receivedAfter");
            String receivedBefore = (String) request.getAttribute("receivedBefore");
            String sortingOrder = (String) request.getAttribute("sortingOrder");
            if (sortingOrder == null) sortingOrder = "";
        %>

        <!-- filter-->
        <form id = "01" method="post">

            <tr>
                <th colspan="5">Filter</th>
            </tr>
            <tr>
                <td>
                    <label>PN</label>
                </td>
                <td>
                        <%
                            if ((pnumber != null) && (!pnumber.equals("null"))) {
                                out.println("<input size = \"35\"  value = \"" + pnumber + "\" type=\"text\" name=\"pnumber\"><br />");
                            }
                            else {
                                out.println("<input size = \"35\"  value = \"\" type=\"text\" name=\"pnumber\"><br />");
                            }
                        %>

                </td>
            </tr>
            <tr>
                <td>
                    <label>Part Name:</label>
                </td>
                <td>
                        <%
                            if ((pname != null) && (!pname.equals("null"))) {
                                out.println("<input size = \"35\" value = \"" + pname + "\" type=\"text\" name=\"pname\"><br />");
                            }
                            else {
                                out.println("<input size = \"35\" value = \"\" type=\"text\" name=\"pname\"><br />");
                            }
                        %>

                </td>
            </tr>
            <tr>
                <td>
                    <label>Vendor:</label>
                </td>
                <td>
                        <%
                            if ((vendor != null) && (!vendor.equals("null"))) {
                                out.println("<input size = \"35\" value = \"" + vendor + "\" type=\"text\" name=\"vendor\"><br />");
                            }
                            else {
                                out.println("<input size = \"35\" value = \"\" type=\"text\" name=\"vendor\"><br />");
                            }
                        %>

                </td>
            </tr>
            <tr>
                <td>
                    <label>Qty:</label>
                </td>
                <td>
                        <%
                            if ((qty != null) && (!qty.equals("null"))) {
                                out.println("<input size = \"6\" value = \"" + qty + "\" type=\"text\" name=\"qty\"><br />");
                            }
                            else {
                                out.println("<input size = \"6\" value = \"\" type=\"text\" name=\"qty\"><br />");
                            }
                        %>

                </td>
            </tr>
            <tr>
                <td>
                    <label>Shipped:</label>
                </td>
                <td>
                        <%
                            if ((shippedAfter != null) && (!shippedAfter.equals("null"))) {
                                out.println("after <input size = \"10\" value = \"" + shippedAfter + "\" type=\"text\" name=\"shippedAfter\">");
                            }
                            else {
                                out.println("after <input  size = \"10\" value = \"\" type=\"text\" name=\"shippedAfter\">");
                            }
                            if ((shippedBefore != null) && (!shippedBefore.equals("null"))) {
                                out.println("before<input size = \"10\" value = \"" + shippedBefore + "\" type = \"text\" name = \"shippedBefore\" ><br / >");
                            }
                            else {
                                out.println("before <input  size = \"10\" value = \"\" type=\"text\" name=\"shippedBefore\">");
                            }
                        %>

                </td>
            </tr>
            <tr>
                <td>
                    <label>Received:</label>
                </td>
                <td>
                        <%
                            if ((receivedAfter != null) && (!receivedAfter.equals("null")))  {
                                out.println("after <input size = \"10\" value = \"" + receivedAfter + "\" type=\"text\" name=\"receivedAfter\">");
                            }
                            else {
                                out.println("after <input  size = \"10\" value = \"\" type=\"text\" name=\"receivedAfter\">");
                            }
                            if ((receivedBefore != null) && (!receivedBefore.equals("null"))) {
                                out.println("before<input size = \"10\" value = \"" + receivedBefore + "\" type = \"text\" name = \"receivedBefore\" ><br / >");
                            }
                            else {
                                out.println("before <input  size = \"10\" value = \"\" type=\"text\" name=\"receivedBefore\">");
                            }
                        %>
                </td>
            </tr>
            <%
                out.println("shipped before = " + shippedBefore);
                out.println("shipped after = " + shippedAfter);
                out.println("received before = " + receivedBefore);
                out.println("received after = " + receivedAfter);
            %>
        </form>
    </table>
<div class = applyFilter>
    <button id = "filterButton" type="submit" form = "01">Filter</button>
</div>
<div>

    <!-- output filtered parts table -->
    <table>
        <tr>
                <!--sort by column-->
                <th class="PnumRes" ><button type="submit" form = "01" name="sortByColumn" value="1">PN</button></th>
                <th class="PnameRes" ><button type="submit" form = "01" name="sortByColumn" value="2">Part Name</button></th>
                <th class="VendorRes"><button type="submit" form = "01" name="sortByColumn" value="3">Vendor</button></th>
                <th class="QtyRes"><button type="submit" form = "01" name="sortByColumn" value="4">Qty</button></th>
                <th class="ShpdRes"><button type="submit" form = "01" name="sortByColumn" value="5">Shipped</button></th>
                <th class="RcvdRes"><button type="submit" form = "01" name="sortByColumn" value="6">Received</button></th>
        </tr>

        <%
            List<Part> parts = (List<Part>) request.getAttribute("listOfParts");
            String formattedShipped = null;     //Shipped date formatted in (MMM dd, yyyy)
            String formattedReceived = null;    //Received date formatted in (MMM dd, yyyy)

            if (parts != null && !parts.isEmpty()) {
                for (Part s : parts) {

                    //format output date
                    if (s.getShipped() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        formattedShipped = sdf.format(s.getShipped());
                    }
                    else formattedShipped = "";
                    if (s.getReceived() != null) {
                        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.US);
                        formattedReceived = sdf.format(s.getReceived());
                    }
                    else formattedReceived = "";

                    //fill output table
                    out.println("<tr><td>" + s.getPartNumber() + "</td>" +
                            "<td>" + s.getPartName() + "</td>" +
                            "<td>" + s.getVendor() + "</td>" +
                            "<td>" + s.getQty() + "</td>" +
                            "<td>" + formattedShipped + "</td>" +
                            "<td>" + formattedReceived + "</td></tr>");
                }
            } else out.println("<div class = outputMessage> <h3> No results matching your request </h3></div>");
        %>
    </table>
</div>

</body>
</html>