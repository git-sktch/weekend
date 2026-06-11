document.addEventListener("DOMContentLoaded", function () {

    var buttons = document.querySelectorAll(".tab-btn");
    var contents = document.querySelectorAll(".tab-content");

    buttons.forEach(function(button) {

        button.addEventListener("click", function() {

            buttons.forEach(function(btn) {
                btn.classList.remove("active");
            });

            contents.forEach(function(content) {
                content.classList.remove("active");
            });

            button.classList.add("active");

            var tabId = button.getAttribute("data-tab");
            var selectedTab = document.getElementById(tabId);

            if (selectedTab) {
                selectedTab.classList.add("active");
            }

        });

    });

});