import random
from faker import Faker
import string
from openpyxl import Workbook
from datetime import datetime, timedelta
from elasticsearch import Elasticsearch
from elasticsearch.helpers import bulk


fake = Faker()
es = Elasticsearch(['http://localhost:9200'])

organizers = [
    {"firstName": "John", "lastName": "Doe"},
    {"firstName": "Emma", "lastName": "Smith"},
    {"firstName": "Michael", "lastName": "Johnson"}
]

curators = [
    {"firstName": "Sophia", "lastName": "Brown"},
    {"firstName": "Daniel", "lastName": "Davis"},
    {"firstName": "Olivia", "lastName": "Martinez"},
    {"firstName": "Alexander", "lastName": "Garcia"},
    {"firstName": "Isabella", "lastName": "Miller"}
]

exhibition_statuses = [
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

#item_categories = [
#    "PAINTING", "DRAWING", "SCULPTURE", "PRINT",
#    "PHOTOGRAPH", "ARTIFACT", "CLOTHING", "SPECIMEN",
#    "FOSSIL", "ANIMAL", "MINERAL", "POTTERY", "JEWELRY"
#]

item_categories = {
    "ANCIENT_HISTORY": ["ARTIFACT", "SCULPTURE", "SPECIMEN", "FOSSIL", "POTTERY", "JEWELRY"],
    "MEDIEVAL_HISTORY": ["ARTIFACT", "SCULPTURE", "CLOTHING", "POTTERY", "JEWELRY"],
    "MODERN_HISTORY": ["ARTIFACT", "CLOTHING", "POTTERY", "JEWELRY"],
    "FINE_ARTS": ["PAINTING", "DRAWING", "SCULPTURE", "PRINT", "PHOTOGRAPH"],
    "CONTEMPORARY_ART": ["PAINTING", "DRAWING", "SCULPTURE", "PRINT", "PHOTOGRAPH"],
    "PHOTOGRAPHY": ["PHOTOGRAPH", "PRINT", "ARTIFACT"],
    "SCULPTURE": ["SCULPTURE", "PHOTOGRAPH", "ARTIFACT"],
    "SCIENCE_AND_TECHNOLOGY": ["SPECIMEN", "FOSSIL", "MINERAL", "ARTIFACT", "PHOTOGRAPH", "PRINT"],
    "NATURAL_HISTORY": ["SPECIMEN", "FOSSIL", "ANIMAL", "MINERAL", "ARTIFACT", "PHOTOGRAPH", "PRINT"],
    "MARITIME": ["SPECIMEN", "FOSSIL", "ANIMAL", "ARTIFACT"],
    "AVIATION": ["ARTIFACT", "PHOTOGRAPH", "PRINT", "CLOTHING"],
    "SPACE_EXPLORATION": ["ARTIFACT", "PHOTOGRAPH", "PRINT", "CLOTHING"],
    "WORLD_CULTURES": ["ARTIFACT", "CLOTHING", "POTTERY", "JEWELRY", "PHOTOGRAPH"],
    "INDIGENOUS_CULTURES": ["ARTIFACT", "CLOTHING", "POTTERY", "JEWELRY"],
    "MUSIC_HISTORY": ["ARTIFACT", "PHOTOGRAPH", "PRINT"],
    "LITERARY_ARTS": ["ARTIFACT", "PRINT"],
    "FASHION_AND_DESIGN": ["CLOTHING", "JEWELRY", "PHOTOGRAPH", "PRINT"],
    "FILM_AND_MEDIA": ["ARTIFACT", "CLOTHING", "JEWELRY", "PRINT", "PHOTOGRAPH"],
    "ARCHAEOLOGY": ["ARTIFACT", "POTTERY", "JEWELRY"],
    "MILITARY_HISTORY": ["ARTIFACT", "CLOTHING", "JEWELRY", "PHOTOGRAPH", "PRINT"],
    "ENVIRONMENTAL_SCIENCE": ["SPECIMEN", "FOSSIL", "ANIMAL", "MINERAL", "ARTIFACT"],
    "CHILDREN_EDUCATION": ["ARTIFACT", "CLOTHING", "PHOTOGRAPH", "DRAWING", "ANIMAL", "PRINT"],
    "SEASONAL": ["ARTIFACT", "CLOTHING", "JEWELRY"]
}

#periods = [
#    "Ancient", "Medieval", "Renaissance", "Baroque", "Modern", "Contemporary",
#    "19th Century", "20th Century", "21st Century"
#]

periods = {
    "ANCIENT_HISTORY": ["Ancient"],
    "MEDIEVAL_HISTORY": ["Medieval"],
    "MODERN_HISTORY": ["Baroque", "19th Century", "20th Century", "21st Century"],
    "FINE_ARTS": ["Renaissance", "Baroque", "19th Century", "20th Century", "21st Century"],
    "CONTEMPORARY_ART": ["21st Century"],
    "PHOTOGRAPHY": ["19th Century", "20th Century", "21st Century"],
    "SCULPTURE": ["Ancient", "Renaissance", "Modern", "Contemporary"],
    "SCIENCE_AND_TECHNOLOGY": ["19th Century", "20th Century", "21st Century"],
    "NATURAL_HISTORY": ["Ancient", "19th Century", "20th Century", "21st Century"],
    "MARITIME": ["Ancient", "19th Century", "20th Century", "21st Century"],
    "AVIATION": ["20th Century", "21st Century"],
    "SPACE_EXPLORATION": ["20th Century", "21st Century"],
    "WORLD_CULTURES": ["Ancient", "Medieval", "Modern", "19th Century", "20th Century", "21st Century"],
    "INDIGENOUS_CULTURES": ["Ancient", "Medieval",  "Modern", "Contemporary"],
    "MUSIC_HISTORY": ["Renaissance", "Baroque", "19th Century", "20th Century", "21st Century"],
    "LITERARY_ARTS": ["Renaissance", "Baroque", "19th Century", "20th Century", "21st Century"],
    "FASHION_AND_DESIGN": ["19th Century", "20th Century", "21st Century"],
    "FILM_AND_MEDIA": ["20th Century", "21st Century"],
    "ARCHAEOLOGY": ["Ancient", "Medieval"],
    "MILITARY_HISTORY": ["19th Century", "20th Century", "21st Century"],
    "ENVIRONMENTAL_SCIENCE": ["20th Century", "21st Century"],
    "CHILDREN_EDUCATION": ["19th Century", "20th Century", "21st Century"],
    "SEASONAL": ["Ancient", "Medieval", "Modern", "19th Century", "20th Century", "21st Century"],
}

item_description_templates = [
    "This {category} from the {period} period is a remarkable example of its kind, showcasing the typical characteristics and craftsmanship of that era.",
    "A fine piece of {category} originating from the {period} period, this item exemplifies the artistic style of its time.",
    "From the {period} period, this {category} stands out for its unique features and historical significance.",
    "An exquisite {category} crafted during the {period} period, representing the culture and techniques of the era.",
    "This {category} is a splendid example from the {period} period, known for its distinctive artistic and cultural value.",
    "Originating in the {period} period, this {category} provides insight into the aesthetics and craftsmanship of that time.",
    "A notable {category} from the {period} period, this item is a testament to the skills and artistic vision of its creator."
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
    start_date = fake.date_between(start_date='-5y', end_date='+1y')
    end_date = fake.date_between(start_date=start_date, end_date=start_date + timedelta(days=365))
    price = random.randint(0, 25)

    exhibition_id = generate_random_id()
    exhibition_name = generate_exhibition_name()
    exhibition_theme = random.choice(exhibition_themes)
    exhibition_status = determine_exhibition_status(start_date, end_date)
    tickets_sold = calculate_tickets_sold(exhibition_status, start_date, end_date)
    items_count = random.randint(1, 5)
    items = [generate_item(exhibition_theme) for _ in range(items_count)]
    reviews = []
    if exhibition_status != "READY_TO_OPEN":
        reviews = [generate_review() for _ in range(random.randint(1, 10))]

    exhibition = {
        "id": exhibition_id,
        "name": exhibition_name,
        "shortDescription": generate_short_description(exhibition_name),
        "longDescription": generate_long_description(exhibition_name, items),
        "theme": exhibition_theme,
        "status": exhibition_status,
        "startDate": start_date.isoformat(),
        "endDate": end_date.isoformat(),
        "price": price,
        "ticketsSold": tickets_sold,
        "organizer": generate_organizer(),
        "curator": generate_curator(),
        "room": generate_room(),
        "items": items,
        "reviews": reviews
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

def calculate_tickets_sold(status, start_date, end_date):
    today = datetime.today().date()
    if status == "READY_TO_OPEN":
        return random.randint(0, 1500)
    elif status == "OPEN":
        days_open = (today - start_date).days
        return days_open * random.randint(100, 1000)
    elif status in ["CLOSED", "ARCHIVED"]:
        days_open = (end_date - start_date).days
        return days_open * random.randint(100, 1000)

def generate_short_description(exhibition_name):
    name_parts = exhibition_name.split()
    key_phrases = " ".join(name_parts[:2])
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

def determine_exhibition_status(start_date, end_date):
    today = datetime.now().date()
    one_year_after_end_date = end_date + timedelta(days=365)

    if start_date > today:
        return "READY_TO_OPEN"
    elif start_date <= today < end_date:
        return "OPEN"
    elif today < one_year_after_end_date:
        return "CLOSED"
    else:
        return "ARCHIVED"


def generate_item(theme):
    if theme not in periods:
        raise ValueError(f"Invalid theme '{theme}'. Theme must be one of: {', '.join(periods.keys())}")

    period_options = periods[theme]
    period = random.choice(period_options)

    category_options = item_categories[theme]
    category = random.choice(category_options)

    name = f"{fake.word().capitalize()} {category.lower()}"

    description_template = random.choice(item_description_templates)
    description = description_template.format(category=category.lower(), period=period.lower())

    authors_name = fake.name()

    year_ranges = {
        "Ancient": (-5000, 500),
        "Medieval": (500, 1500),
        "Renaissance": (1300, 1600),
        "Baroque": (1600, 1750),
        "Modern": (1750, 1900),
        "Contemporary": (1900, 2024),
        "19th Century": (1801, 1900),
        "20th Century": (1901, 2000),
        "21st Century": (2001, 2024)
    }
    start_year, end_year = year_ranges[period]
    year_of_creation = random.randint(start_year, end_year)

    return {
        "name": name,
        "description": description,
        "authorsName": authors_name,
        "yearOfCreation": str(year_of_creation),
        "period": period,
        "category": category
    }

def generate_room():
    floor = random.randint(1, 5)
    room_base_number = floor * 10 + random.randint(0,9)
    room_number = str(room_base_number)
    if random.choice([True, False]):  # 50% chance to add a letter
        room_number += random.choice('abcde')
    return {
        "floor": str(floor),
        "number": room_number
    }

def generate_organizer():
    organizer = random.choice(organizers)
    return {
        "firstName": organizer['firstName'],
        "lastName": organizer['lastName']
    }

def generate_curator():
    curator = random.choice(curators)
    return {
        "firstName": curator['firstName'],
        "lastName": curator['lastName']
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

def generate_exhibitions(num):
    return [generate_exhibition() for _ in range(num)]

def save_to_excel(data, filename):
    wb = Workbook()
    ws = wb.active

    header = [
        'id', 'name', 'shortDescription', 'longDescription', 'theme', 'status', 'startDate', 'endDate',
        'price', 'ticketsSold', 'organizer_firstName', 'organizer_lastName',
        'curator_firstName', 'curator_lastName', 'room_floor',
        'room_number', 'items', 'reviews'
    ]

    ws.append(header)

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
            exhibition['room']['floor'],
            exhibition['room']['number'],
            ' | '.join(items),
            ' | '.join(reviews)
        ]
        ws.append(row)

    wb.save(filename)

def index_exhibitions_to_elasticsearch(exhibitions):
    actions = []
    for exhibition in exhibitions:
        action = {
            "_index": "exhibition",  # Elasticsearch index name
            "_source": {
                "_class": ["rs.ac.uns.acs.nais.exhibition_service.model.Exhibition"],
                "name": exhibition['name'],
                "shortDescription": exhibition['shortDescription'],
                "longDescription": exhibition['longDescription'],
                "theme": exhibition['theme'],
                "status": exhibition['status'],
                "startDate": exhibition['startDate'],
                "endDate": exhibition['endDate'],
                "price": exhibition['price'],
                "ticketsSold": exhibition['ticketsSold'],
                "organizer": exhibition['organizer'],
                "curator": exhibition['curator'],
                "room": exhibition['room'],
                "items": exhibition['items'],
                "reviews": exhibition['reviews']
            }
        }
        actions.append(action)

    # Use Elasticsearch bulk helper to efficiently index multiple documents
    success, _ = bulk(es, actions)
    print(f"Indexed {success} documents")

if __name__ == "__main__":
    num_exhibitions = 1000
    exhibitions = generate_exhibitions(num_exhibitions)
    save_to_excel(exhibitions, 'exhibitions.xlsx')
    index_exhibitions_to_elasticsearch(exhibitions)
    if es.indices.exists(index="exhibition"):
        print("Index 'exhibition' already exists.")


