$.ajax({
    url: "/content/weekend/us/en.products.json",
    type: "GET",
    dataType: "json",
    success: function (response) {

        var html = "";

        $.each(response, function (index, car) {
            html += "<div class='card'>";
            html += "<h3>" + car.car + "</h3>";
            html += "<p><strong>Price :</strong> $" + car.price + "</p>";
            html += "<p><strong>Color :</strong> " + car.color + "</p>";
            html += "</div>";
        });

        $("#carList").html(html);
    },
    error: function () {
        $("#carList").html("<h3>Unable to load products.</h3>");
    }
});