const questionContainer = document.getElementById("question-container");
const resultContainer = document.getElementById("result-container");
const questionElement = document.getElementById("question");
const answersContainer = document.getElementById("answers-container"); // Изменили на контейнер для ответов
const nextButton = document.getElementById("next-button");
const scoreElement = document.getElementById("score");
const answerCheckboxes = document.querySelectorAll(".answer-checkbox");

const test = document.getElementById("question-container").getAttribute("data");

let questions = [];
let currentQuestionIndex = 0;
const userAnswers = []
let correctAnswers = 0;
let correctQuestions = []

// Fetch questions and answers from the server
fetch(`/quiz/${test}`)
    .then(response => response.json())
    .then(data => {
        questions = data;
        showQuestion();
    })
    .catch(error => console.error('Error fetching questions:', error));


function sendAnswer(data) {
    fetch('/quiz_save', options={
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    })
        .then(response => response.json())
        .then(data => {
            console.log(data);
        })
        .catch(error => {
            console.error('Произошла ошибка:', error);
        });
}

function showQuestion() {
    const currentQuestion = questions[currentQuestionIndex];
    questionElement.textContent = `Вопрос ${currentQuestionIndex + 1}: ${currentQuestion.questionText}`;

    // Очистить предыдущие ответы
    answersContainer.innerHTML = "";

    // Отобразить варианты ответов
    currentQuestion.answers.forEach((answer, index) => {
        const answerCheckbox = document.createElement("input");
        answerCheckbox.type = "checkbox";
        answerCheckbox.className = "answer-checkbox";
        answerCheckbox.id = `answer${index + 1}`;

        const answerLabel = document.createElement("label");
        answerLabel.htmlFor = `answer${index + 1}`;
        answerLabel.textContent = answer.answerText;

        answersContainer.appendChild(answerCheckbox)
        answersContainer.appendChild(answerLabel);
    });
}
function showResult() {
    questionContainer.style.display = "none";
    resultContainer.style.display = "block";
    let totalCorrectAnswers = 0;

    questions.forEach((question) => {
        const correctAnswersIndices = question.answers
            .map((answer, index) => answer.isCorrect ? index : -1)
            .filter(index => index !== -1);

        const isCorrect = userAnswers.every(answerIndex => correctAnswersIndices.includes(answerIndex));
        console.log(isCorrect)
        if (isCorrect) {
            totalCorrectAnswers++;
            correctQuestions.push(question);
        }
    });

    const resultQuiz = {
        'testId': test,
        'correctAnswersCount': totalCorrectAnswers,
        'questionsId': correctQuestions
    };
    sendAnswer(resultQuiz);
    scoreElement.textContent = `Вы ответили правильно на ${totalCorrectAnswers} из ${questions.length} вопросов.`;
}

function checkAnswer() {
    const currentQuestion = questions[currentQuestionIndex];
    const userAnswers = [];

    for (let i = 0; i < currentQuestion.answers.length; i++) {
        const answerCheckbox = document.getElementById(`answer${i + 1}`);
        if (answerCheckbox.checked) {
            userAnswers.push(i);
        }
    }

    // Проверяем, выбран ли хотя бы один ответ
    if (userAnswers.length === 0) {
        alert("Пожалуйста, выберите хотя бы один ответ.");
        return;
    }

    // Сохраняем выбранные ответы для текущего вопроса
    currentQuestion.userAnswers = userAnswers;
    currentQuestionIndex++;

    if (currentQuestionIndex < questions.length) {
        showQuestion();
    } else {
        // После последнего вопроса проверяем все ответы
        showResult();
    }
}

nextButton.addEventListener("click", function () {
    checkAnswer();
});

showQuestion();
