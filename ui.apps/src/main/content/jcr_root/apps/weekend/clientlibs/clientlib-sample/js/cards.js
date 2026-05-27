document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".card__button").forEach(function (button) {
        button.onclick = function () {
            alert("Next page");
        };
    });
});
