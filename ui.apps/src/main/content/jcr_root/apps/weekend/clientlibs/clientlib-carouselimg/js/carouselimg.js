(function () {
    "use strict";

    [].forEach.call(document.querySelectorAll(".carouselimg"), function (carousel) {
        var slides = carousel.querySelectorAll(".carouselimg__slide"), index = 0;
        var prev = carousel.querySelector(".carouselimg__button--prev");
        var next = carousel.querySelector(".carouselimg__button--next");

        function show(nextIndex) {
            index = (nextIndex + slides.length) % slides.length;
            [].forEach.call(slides, function (slide, slideIndex) {
                slide.classList.toggle("carouselimg__slide--active", slideIndex === index);
            });
        }

        if (!slides.length) { return; }
        if (prev) { prev.addEventListener("click", function () { show(index - 1); }); }
        if (next) { next.addEventListener("click", function () { show(index + 1); }); }
        show(0);
    });
}());
