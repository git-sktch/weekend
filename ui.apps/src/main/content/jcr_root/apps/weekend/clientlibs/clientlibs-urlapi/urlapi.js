(function () {

    console.log("JS FILE LOADED");

    document.addEventListener("DOMContentLoaded", function () {

        console.log("DOM READY");

        var containers = document.querySelectorAll(".urlapi-container");

        containers.forEach(function (container) {

            var button = container.querySelector("#loadApiBtn");
            var status = container.querySelector("#statusMsg");
            var result = container.querySelector("#apiResult");
            var apiUrlInput = container.querySelector("#apiUrl");
            var limitInput = container.querySelector("#limit");

            if (!button || !status || !result || !apiUrlInput || !limitInput) {

                console.log("Component elements not found");

                return;

            }

            status.innerHTML = "JavaScript Loaded Successfully";

            button.addEventListener("click", function () {

                status.innerHTML = "Calling Servlet...";

                var apiUrl = apiUrlInput.value;
                var limit = limitInput.value;

                fetch("/bin/urlapi?url=" +
                    encodeURIComponent(apiUrl) +
                    "&limit=" +
                    limit)

                    .then(function (response) {

                        status.innerHTML = "Servlet Working...";

                        if (!response.ok) {

                            throw new Error("HTTP Error : " + response.status);

                        }

                        return response.json();

                    })

                    .then(function (data) {

                        status.innerHTML = "Data Loaded Successfully";

                        console.log(data);

                        var items = [];

                        if (Array.isArray(data)) {

                            items = data;

                        } else if (data.carts && data.carts.length > 0) {

                            items = data.carts[0].products;

                        } else {

                            for (var key in data) {

                                if (Array.isArray(data[key])) {

                                    items = data[key];

                                    break;

                                }

                            }

                        }

                        if (items.length === 0) {

                            result.innerHTML = "<h3>No Data Found</h3>";

                            return;

                        }

                        var html = "";

                        items.forEach(function (item) {

                            html += '<div class="urlapi-card">';

                            if (item.thumbnail) {

                                html +=
                                    '<img src="' +
                                    item.thumbnail +
                                    '" alt="' +
                                    (item.title || "Image") +
                                    '">';

                            }

                            html +=
                                '<h3>' +
                                (item.title || item.name || "Item") +
                                '</h3>';

                            if (item.price !== undefined) {

                                html +=
                                    '<p><strong>Price :</strong> $' +
                                    item.price +
                                    '</p>';

                            }

                            if (item.quantity !== undefined) {

                                html +=
                                    '<p><strong>Quantity :</strong> ' +
                                    item.quantity +
                                    '</p>';

                            }

                            if (item.total !== undefined) {

                                html +=
                                    '<p><strong>Total :</strong> $' +
                                    item.total +
                                    '</p>';

                            }

                            if (item.discountPercentage !== undefined) {

                                html +=
                                    '<p><strong>Discount :</strong> ' +
                                    item.discountPercentage +
                                    '%</p>';

                            }

                            html += '</div>';

                        });

                        result.innerHTML = html;

                    })

                    .catch(function (error) {

                        console.log(error);

                        status.innerHTML = "Servlet Error";

                        result.innerHTML =
                            "<h3>" + error.message + "</h3>";

                    });

            });

        });

    });

})();