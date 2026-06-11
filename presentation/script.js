const slides = document.querySelectorAll('.slide');
const prevBtn = document.getElementById('prevBtn');
const nextBtn = document.getElementById('nextBtn');
const progressBar = document.getElementById('progressBar');

let currentSlide = 0;

function updateSlides() {
    slides.forEach((slide, index) => {
        slide.classList.remove('active', 'prev');
        if (index === currentSlide) {
            slide.classList.add('active');
        } else if (index < currentSlide) {
            slide.classList.add('prev');
        }
    });

    // Update Progress Bar
    const progress = ((currentSlide + 1) / slides.length) * 100;
    progressBar.style.width = `${progress}%`;

    // Update buttons visibility
    prevBtn.style.visibility = currentSlide === 0 ? 'hidden' : 'visible';
    nextBtn.style.visibility = currentSlide === slides.length - 1 ? 'hidden' : 'visible';
}

function nextSlide() {
    if (currentSlide < slides.length - 1) {
        currentSlide++;
        updateSlides();
    }
}

function prevSlide() {
    if (currentSlide > 0) {
        currentSlide--;
        updateSlides();
    }
}

// Event Listeners
nextBtn.addEventListener('click', nextSlide);
prevBtn.addEventListener('click', prevSlide);

document.addEventListener('keydown', (e) => {
    if (e.key === 'ArrowRight' || e.key === ' ' || e.key === 'ArrowDown') {
        nextSlide();
    } else if (e.key === 'ArrowLeft' || e.key === 'ArrowUp') {
        prevSlide();
    }
});

// Initialize
updateSlides();
