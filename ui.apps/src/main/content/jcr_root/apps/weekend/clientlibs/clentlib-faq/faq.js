document.addEventListener("DOMContentLoaded", function () {

    var questions = document.querySelectorAll(".faq-question");

    questions.forEach(function (question) {

        question.addEventListener("click", function () {

            var answer = this.nextElementSibling;

            answer.classList.toggle("active");
        });

    });

    document.querySelector(".expand-all")
        .addEventListener("click", function () {

            document.querySelectorAll(".faq-answer")
                .forEach(function (answer) {

                    answer.classList.add("active");
                });
        });

    document.querySelector(".collapse-all")
        .addEventListener("click", function () {

            document.querySelectorAll(".faq-answer")
                .forEach(function (answer) {

                    answer.classList.remove("active");
                });
        });
});