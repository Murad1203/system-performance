

// Функция для получения данных из формы и создания JSON
function getFormData() {
    const formData = {
        testName: document.getElementById("testName").value,
        questions: [],
    };

    // Получение всех вопросов
    const questionDivs = document.querySelectorAll('.question');

    questionDivs.forEach((questionDiv, questionIndex) => {
        const questionData = {
            name: questionDiv.querySelector(`#questionText${questionIndex + 1}`).value,
            answers: [],
            award: document.getElementById(`awardQuest${questionIndex + 1}`).value
        };

        // Получение всех ответов для данного вопроса
        const answerInputs = questionDiv.querySelectorAll(`#answers${questionIndex + 1} li`);

        answerInputs.forEach((li, answerIndex) => {
            const input = li.querySelector(`input[type="text"]`);

            questionData.answers.push({
                name: input.value,
                isCorrect: answerIndex === parseInt(questionDiv.querySelector(`#correctAnswer${questionIndex + 1}`).value),
            });
            console.log(typeof questionData.answers.isCorrect)
        });

        formData.questions.push(questionData); // Добавляем questionData в массив questions
    });
    console.log(formData)
    return formData;
}

submitTest = document.getElementById("buttonTest");



function sendDataToServer() {
    const data = getFormData();

    console.log(data)
    fetch('/add-test', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data),
    })
        .then(response => {
            if (response.ok) {
                return response.json();
            } else {
                throw new Error('Ошибка при отправке данных на сервер');
            }
        })
        .then(data => {
            console.log(data);
            // Добавьте обработку успешного ответа здесь
        })
        .catch(error => {
            console.error(error);
            // Добавьте обработку ошибки здесь
        });

}


submitTest.addEventListener('click', () => {
    sendDataToServer();
    window.location.href = "/";
})