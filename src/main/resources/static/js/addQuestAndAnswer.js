let questionCounter = 1;

function addQuestion() {
    const questionsContainer = document.getElementById("questionsContainer");
    const questionDiv = document.createElement("div");
    questionDiv.classList.add("question");
    questionDiv.innerHTML = `
                <h3>Question ${questionCounter}</h3>
                <label for="questionText${questionCounter}">Question:</label>
                <textarea id="questionText${questionCounter}" name="questionText[]" required></textarea>
                <br>
                <input id="awardQuest${questionCounter}" type="number">
                <br>
                <label for="answers${questionCounter}">Answers:</label>
                <ul id="answers${questionCounter}">
                </ul>
                <button type="button" onclick="addAnswer(${questionCounter})">Add Answer</button>
                <br>
                
                <label for="correctAnswer${questionCounter}">Correct Answer:</label>
                <select id="correctAnswer${questionCounter}" name="correctAnswer[]">
                    <!-- Options will be populated dynamically using JavaScript -->
                </select>
                <br>

                <button type="button" onclick="removeQuestion(0)">Remove Question</button>
            `;
    questionsContainer.appendChild(questionDiv);
    updateCorrectAnswerOptions(questionCounter);
    makeAnswersDraggable(questionCounter);
    updateQuestionIndexes();
    questionCounter++;
}

let answerIdCounter = 0;

function addAnswer(questionIndex) {
    const ul = document.getElementById(`answers${questionIndex}`);
    const li = document.createElement("li");
    li.draggable = true;
    li.className = "draggable";
    const answerId = answerIdCounter++; // Увеличиваем счетчик идентификаторов
    li.innerHTML = `
                <input type="text" name="answer[${questionIndex}][${answerId}]" required>
                <button type="button" onclick="removeAnswer(this)">Remove</button>
            `;
    ul.appendChild(li);
    updateCorrectAnswerOptions(questionIndex);
    makeAnswersDraggable(questionIndex);
}
function removeAnswer(button) {
    const li = button.parentElement;
    li.parentElement.removeChild(li);
}

function removeQuestion(questionIndex) {
    const questionDiv = document.querySelector(`#questionsContainer .question:nth-child(${questionIndex + 1})`);
    if (questionDiv) {
        questionDiv.remove();
    }
    // После удаления вопроса, пересчитайте индексы вопросов
    updateQuestionIndexes();
}

function updateQuestionIndexes() {
    const questionDivs = document.querySelectorAll("#questionsContainer .question");
    questionDivs.forEach((questionDiv, index) => {
        questionDiv.querySelector("h3").textContent = `Question ${index + 1}`;
    });
    // Обновите индексы для полей ответов и правильных ответов
    updateAnswerIndexes();
}

function updateAnswerIndexes() {
    const questionDivs = document.querySelectorAll("#questionsContainer .question");
    questionDivs.forEach((questionDiv, questionIndex) => {
        const answerInputs = questionDiv.querySelectorAll(`#answers${questionIndex + 1} input[name='answer[]']`);
        answerInputs.forEach((input, answerIndex) => {
            input.name = `answer[${questionIndex}][${answerIndex}]`;
        });

        const correctAnswerSelect = questionDiv.querySelector(`#correctAnswer${questionIndex + 1}`);
        correctAnswerSelect.name = `correctAnswer[${questionIndex}]`;
        correctAnswerSelect.id = `correctAnswer${questionIndex + 1}`;
    });
}

// JavaScript to dynamically populate the correct answer dropdown
function updateCorrectAnswerOptions(questionIndex) {
    const correctAnswerDropdown = document.getElementById(`correctAnswer${questionIndex}`);
    correctAnswerDropdown.innerHTML = '';
    const answersList = document.querySelectorAll(`#answers${questionIndex} input[type="text"][name^="answer[${questionIndex}]"]`);
    answersList.forEach((answer, answerIndex) => {
        const option = document.createElement("option");
        option.value = answerIndex;
        option.text = `Answer ${answerIndex + 1}`;
        correctAnswerDropdown.appendChild(option);
    });
}


// JavaScript for drag-and-drop functionality
function makeAnswersDraggable(questionIndex) {
    const draggableList = document.querySelectorAll(`#answers${questionIndex} .draggable`);

    draggableList.forEach((item) => {
        item.addEventListener("dragstart", (e) => {
            e.dataTransfer.setData("text/plain", item.innerHTML);
            e.dataTransfer.effectAllowed = "move";
            item.classList.add("dragging");
        });

        item.addEventListener("dragend", () => {
            draggableList.forEach((draggable) => {
                draggable.classList.remove("dragging");
            });
        });

        item.addEventListener("dragover", (e) => {
            e.preventDefault();
            const afterElement = getDragAfterElement(item, e.clientY);
            const container = item.parentElement;

            if (afterElement == null) {
                container.appendChild(item);
            } else {
                container.insertBefore(item, afterElement);
            }
        });

        item.addEventListener("dragenter", (e) => {
            e.preventDefault();
            item.style.transform = "scale(1.1)";
        });

        item.addEventListener("dragleave", () => {
            item.style.transform = "scale(1)";
        });

        item.addEventListener("drop", () => {
            item.style.transform = "scale(1)";
        });
    });
}


function getDragAfterElement(item, y) {
    const draggableElements = [...item.parentElement.querySelectorAll(".draggable:not(.dragging)")];

    return draggableElements.reduce(
        (closest, child) => {
            const box = child.getBoundingClientRect();
            const offset = y - box.top - box.height / 2;

            if (offset < 0 && offset > closest.offset) {
                return { offset, element: child };
            } else {
                return closest;
            }
        },
        { offset: Number.NEGATIVE_INFINITY }
    ).element;
}