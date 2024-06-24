import random
from faker import Faker
import string
from openpyxl import Workbook
from datetime import datetime, timedelta

#inicijalizacija fakera
fake = Faker()

#enumi
exhibition_statuses = [
    "PROPOSED", "CURATING", "AWAITING_APPROVAL",
    "READY_TO_OPEN", "OPEN", "CLOSED", "ARCHIVED"
]
exhibition_themes = [
    "ANCIENT_HISTORY", "MEDIEVAL_HISTORY", "MODERN_HISTORY",
    "FINE_ARTS", "CONTEMPORARY_ART", "PHOTOGRAPHY", "SCULPTURE",
    "SCIENCE_AND_TECHNOLOGY", "NATURAL_HISTORY", "MARITIME",
    "AVIATION", "SPACE_EXPLORATION", "WORLD_CULTURES",
    "INDIGENOUS_CULTURES", "MUSIC_HISTORY", "LITERARY_ARTS",
    "FASHION_AND_DESIGN", "FILM_AND_MEDIA", "ARCHAEOLOGY",
    "MILITARY_HISTORY", "ENVIRONMENTAL_SCIENCE", "CHILDREN_EDUCATION",
    "SEASONAL"
]
item_categories = [
    "PAINTING", "DRAWING", "SCULPTURE", "PRINT",
    "PHOTOGRAPH", "ARTIFACT", "CLOTHING", "SPECIMEN",
    "FOSSIL", "ANIMAL", "MINERAL", "POTTERY", "JEWELRY"
]

adjectives = [
    "Ancient", "Medieval", "Modern", "Contemporary", "Fascinating", "Incredible",
    "Remarkable", "Enigmatic", "Historic", "Mystical", "Innovative", "Timeless",
    "Cultural", "Artistic", "Scientific", "Technological", "Natural", "Maritime",
    "Aviation", "Space", "World", "Indigenous", "Musical", "Literary", "Fashionable",
    "Film", "Media", "Archaeological", "Military", "Environmental", "Educational",
    "Seasonal"
]

nouns = [
    "History", "Art", "Photography", "Sculpture", "Science", "Technology", "Nature",
    "Exploration", "Cultures", "Music", "Literature", "Design", "Cinema", "Media",
    "Archaeology", "Military", "Environment", "Education", "Children", "Exhibition",
    "Showcase", "Display", "Gallery", "Collection", "Archive", "Exhibit", "Experience"
]

phrases = [
    "through the Ages", "and Beyond", "in Context", "of the World", "in Focus",
    "Unveiled", "Reimagined", "Explored", "Rediscovered", "Uncovered", "and the Future",
    "Revisited", "Transcending Time", "and Innovation", "and Heritage", "Past to Present"
]

additional_descriptive_phrases = [
    "showcases a unique collection of artifacts and stories.",
    "offers an in-depth look into historical events and achievements.",
    "provides a comprehensive overview of artistic expressions and innovations.",
    "is a remarkable display of cultural heritage and artistic talent.",
    "highlights the beauty of human creativity and scientific progress.",
    "presents an exploration of technological advancements and their impact.",
    "delivers an insightful experience into diverse cultures and histories.",
    "uncovers the history of significant periods and their influence on today.",
    "explores the fascinating world of artistic masterpieces and historical artifacts.",
    "delves into the intricate details of various historical and cultural phenomena.",
    "celebrates the wonder of human ingenuity and artistic expression."
]

# Long description components
long_description_introductions = [
    "Step into the world of", "Discover the fascinating journey of", "Explore the rich history and culture of",
    "Embark on a captivating exploration of", "Immerse yourself in the stories and artifacts of",
    "Uncover the secrets and marvels of", "Experience the beauty and wonder of"
]

long_description_backgrounds = [
    "This exhibition delves into the historical significance and cultural impact of",
    "Visitors will gain insight into the evolution and development of",
    "Through a diverse collection of artifacts and exhibits, this exhibition highlights",
    "The exhibition provides an in-depth look into the milestones and achievements of",
    "A comprehensive overview of the periods and events that shaped",
    "The exhibition showcases the artistic expressions and innovations that define"
]

long_description_highlights = [
    "Among the highlights, visitors will find", "Notable features include", "Key exhibits include",
    "Highlights of the exhibition are", "Visitors can expect to see", "Standout pieces include"
]

long_description_experiences = [
    "The exhibition offers an interactive and engaging experience, allowing visitors to",
    "Guests can immerse themselves in the multimedia displays and interactive elements, providing a unique perspective on",
    "The layout of the exhibition is designed to guide visitors through the different periods and themes of",
    "An enriching experience awaits, with detailed explanations and informative displays on",
    "Visitors are encouraged to explore at their own pace, discovering the various aspects of"
]

long_description_conclusions = [
    "Don't miss the opportunity to explore this unique exhibition and gain a deeper understanding of",
    "This exhibition is a must-see for anyone interested in",
    "We invite you to discover the fascinating stories and remarkable artifacts of",
    "Join us in celebrating the rich heritage and innovative spirit of",
    "This is an unforgettable experience that highlights the importance and beauty of"
]

positive_review_beginnings = [
    "A fascinating exploration of",
    "An amazing collection of",
    "The exhibition was well-organized and",
    "The curator did a fantastic job with",
    "I found the exhibition to be very",
    "A must-see for anyone interested in",
    "The art pieces were simply",
    "I loved the interactive displays and",
    "It was an educational experience with",
    "The exhibition showcased a brilliant"
]
positive_review_middles = [
    "space and beyond.",
    "artifacts and exhibits.",
    "informative displays.",
    "the theme and presentation.",
    "inspiring and insightful.",
    "history and culture.",
    "stunning and captivating.",
    "engaging exhibits.",
    "informative guides.",
    "collection of works."
]
positive_review_endings = [
    "Highly recommend!",
    "A bit crowded, but worth it.",
    "An unforgettable experience.",
    "A delightful experience.",
    "Very educational and fun.",
    "Will visit again!",
    "Perfect for families.",
    "A real eye-opener.",
    "Not to be missed.",
    "Thoroughly enjoyed it."
]

negative_review_beginnings = [
    "A disappointing collection of",
    "The exhibition was poorly organized and",
    "The curator did a terrible job with",
    "I found the exhibition to be very",
    "Not a great display of",
    "The art pieces were",
    "The interactive displays were",
    "It was a boring experience with",
    "The exhibition had a lackluster"
]

negative_review_middles = [
    "space and beyond.",
    "artifacts and exhibits.",
    "informative displays.",
    "the theme and presentation.",
    "uninspiring and dull.",
    "history and culture.",
    "mediocre and uninteresting.",
    "engaging exhibits.",
    "informative guides.",
    "collection of works."
]

negative_review_endings = [
    "Not recommended.",
    "A waste of time.",
    "Forgettable experience.",
    "Very disappointing.",
    "Won't visit again.",
    "Not worth the visit.",
    "A letdown for families.",
    "Not an eye-opener.",
    "Definitely to be missed.",
    "Didn't enjoy it."
]

def generate_exhibition():
    start_date = fake.date_between(start_date='-1y', end_date='today')
    end_date = fake.date_between(start_date=start_date, end_date='+1y') if random.choice([True, False]) else None
    price = random.randint(0, 50)
    tickets_sold = random.randint(0, 1000)

    exhibition_id = generate_random_id()
    exhibition_name = generate_exhibition_name()
    items_count = random.randint(1, 5)
    items = [generate_item() for _ in range(items_count)]

    exhibition = {
        "id": exhibition_id,
        "name": exhibition_name,
        "shortDescription": generate_short_description(exhibition_name),
        "longDescription": generate_long_description(exhibition_name, items),
        "theme": random.choice(exhibition_themes),
        "status": random.choice(exhibition_statuses),
        "startDate": start_date.isoformat(),
        "endDate": end_date.isoformat() if end_date else None,
        "price": price,
        "ticketsSold": tickets_sold,
        "organizer": generate_organizer(),
        "curator": generate_curator(),
        "room": generate_room(),
        "items": items,
        "reviews": [generate_review() for _ in range(random.randint(1, 10))]
    }

    return exhibition

def generate_random_id(length=20):
    characters = string.ascii_letters + string.digits
    return ''.join(random.choice(characters) for _ in range(length))

def generate_exhibition_name():
    name = random.choice(adjectives) + " " + random.choice(nouns)
    if random.random() > 0.5:
        name += " " + random.choice(phrases)
    return name

def generate_short_description(exhibition_name):
    name_parts = exhibition_name.split()
    key_phrases = " ".join(name_parts[:2])  # Use first two words for simplicity
    description = f"{key_phrases} {random.choice(additional_descriptive_phrases)}."
    return description


def generate_long_description(exhibition_name, items):
    introduction = f"{random.choice(long_description_introductions)} {exhibition_name}."
    background = f"{random.choice(long_description_backgrounds)} {exhibition_name}."

    if items:
        item_names = [item["name"] for item in items]
        highlight_phrase = random.choice(long_description_highlights)
        items_highlight = " ".join(random.sample(item_names, min(len(item_names), 5)))
        highlights = f"{highlight_phrase} {items_highlight}."
    else:
        highlights = random.choice(long_description_highlights)

    experience = f"{random.choice(long_description_experiences)} {exhibition_name}."
    conclusion = f"{random.choice(long_description_conclusions)} {exhibition_name}."
    long_description = f"{introduction} {background} {highlights} {experience} {conclusion}"
    return long_description

def generate_item():
    return {
        "name": fake.word(),
        "description": fake.paragraph(nb_sentences=2),
        "authorsName": fake.name(),
        "yearOfCreation": str(fake.year()),
        "period": fake.word(),
        "category": random.choice(item_categories)
    }

def generate_room():
    return {
        "name": fake.word(),
        "floor": str(random.randint(1, 5)),
        "number": str(random.randint(100, 500))
    }

def generate_organizer():
    return {
        "firstName": fake.first_name(),
        "lastName": fake.last_name()
    }

def generate_curator():
    return {
        "firstName": fake.first_name(),
        "lastName": fake.last_name()
    }

def generate_review():
    rating = random.randint(1, 5)
    if rating > 2:
        text = f"{random.choice(positive_review_beginnings)} {random.choice(positive_review_middles)} {random.choice(positive_review_endings)}"
    else:
        text = f"{random.choice(negative_review_beginnings)} {random.choice(negative_review_middles)} {random.choice(negative_review_endings)}"
    return {
        "reviewer": fake.name(),
        "text": text,
        "rating": rating
    }

# Generate a list of exhibitions
def generate_exhibitions(num):
    return [generate_exhibition() for _ in range(num)]


def save_to_excel(data, filename):
    # Create a new workbook
    wb = Workbook()
    ws = wb.active  # Get the active worksheet

    # Define header row
    header = [
        'id', 'name', 'shortDescription', 'longDescription', 'theme', 'status', 'startDate', 'endDate',
        'price', 'ticketsSold', 'organizer_firstName', 'organizer_lastName',
        'curator_firstName', 'curator_lastName', 'room_name', 'room_floor',
        'room_number', 'items', 'reviews'
    ]

    # Write header row
    ws.append(header)

    # Write each exhibition as a row in the Excel worksheet
    for exhibition in data:
        items = [
            f"Name: {item['name']}, Description: {item['description']}, AuthorsName: {item['authorsName']}, YearOfCreation: {item['yearOfCreation']}, Period: {item['period']}, Category: {item['category']}"
            for item in exhibition['items']
        ]
        reviews = [
            f"Reviewer: {review['reviewer']}, Rating: {review['rating']}, Text: {review['text']}"
            for review in exhibition['reviews']
        ]

        row = [
            exhibition['id'],
            exhibition['name'],
            exhibition['shortDescription'],
            exhibition['longDescription'],
            exhibition['theme'],
            exhibition['status'],
            exhibition['startDate'],
            exhibition['endDate'],
            exhibition['price'],
            exhibition['ticketsSold'],
            exhibition['organizer']['firstName'],
            exhibition['organizer']['lastName'],
            exhibition['curator']['firstName'],
            exhibition['curator']['lastName'],
            exhibition['room']['name'],
            exhibition['room']['floor'],
            exhibition['room']['number'],
            ' | '.join(items),
            ' | '.join(reviews)
        ]
        ws.append(row)

    # Save workbook to filename
    wb.save(filename)

if __name__ == "__main__":
    num_exhibitions = 1000  # Change this number as needed
    exhibitions = generate_exhibitions(num_exhibitions)
    save_to_excel(exhibitions, 'exhibitions.xlsx')

