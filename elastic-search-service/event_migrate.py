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

adjectives = [
    "Exciting", "Engaging", "Interactive", "Inspiring", "Dynamic", "Vibrant",
    "Lively", "Educational", "Entertaining", "Captivating", "Enlightening",
    "Fun", "Informative", "Motivational", "Thought-Provoking", "Innovative",
    "Creative", "Relaxing", "Social", "Adventurous"
]


nouns = [
    "Workshop", "Seminar", "Conference", "Symposium", "Lecture", "Meeting",
    "Gathering", "Festival", "Fair", "Celebration", "Concert", "Performance",
    "Showcase", "Exhibit", "Demonstration", "Presentation", "Discussion",
    "Forum", "Panel", "Talk", "Debate", "Show", "Activity", "Class", "Course",
    "Training", "Session"
]


phrases = [
    "for Beginners", "for Experts", "for Everyone", "for Families", "for Professionals",
    "for Enthusiasts", "and Networking", "and Discussion", "and Learning", "and Fun",
    "with Hands-on Activities", "and Insights", "and Innovations", "and Discoveries",
    "and More", "with Q&A", "with Live Music", "with Guest Speakers", "and Exhibits",
    "and Demonstrations"
]


description_introductions = [
    "Join us for", "Don't miss", "Be part of", "Experience", "Get ready for",
    "Discover", "Participate in", "Enjoy", "Take part in", "Learn from",
    "Explore", "Celebrate", "Engage in", "Immerse yourself in", "Embark on",
    "Witness", "Encounter", "Uncover", "Delve into", "Embrace"
]


description_backgrounds = [
    "This event will cover various aspects of the subject matter.",
    "Attendees will learn about the significance of this topic.",
    "Topics include a wide range of related subjects.",
    "We will discuss key aspects related to this theme.",
    "Key points will include pivotal moments in this field.",
    "The event will focus on exploring different facets of the topic.",
    "Main subjects include important elements of the subject.",
    "Highlights of the event are engaging discussions and presentations.",
    "Our focus will be on examining the impact of this subject.",
    "We invite you to explore diverse perspectives on this topic.",
    "Expect to discover new insights into this historical period.",
    "Learn about the history and development of this field.",
    "Gain insights into the cultural and societal impact.",
    "Explore the impact of technological advancements.",
    "Dive deep into the scientific discoveries.",
    "Experience the evolution of ideas and innovations.",
    "Witness the beauty of artistic expressions."
]

description_experiences = [
    "You'll experience interactive exhibits and engaging displays.",
    "Participants will enjoy hands-on activities and informative sessions.",
    "Expect to see a variety of artifacts and multimedia presentations.",
    "Highlights include dynamic performances and interactive workshops.",
    "Activities will involve interactive learning stations and demonstrations.",
    "The event will feature live demonstrations and expert panels.",
    "You will take part in discussions with industry leaders.",
    "Enjoy hands-on activities with state-of-the-art technology.",
    "Engage with immersive experiences and virtual reality showcases.",
    "Explore interactive exhibits on the history of this theme.",
    "Discover unique perspectives on this significant topic.",
    "Interact with experts on various aspects of the subject.",
    "Participate in engaging discussions about current trends.",
    "Get a closer look at rare artifacts and historical documents.",
    "Learn from the pioneers of this field through insightful presentations.",
    "Take a journey through different eras and cultural milestones.",
    "Discover the untold stories behind significant events."
]

description_conclusions = [
    "Make sure to join us!", "This is an event you don't want to miss.",
    "We look forward to seeing you.", "Join us for an unforgettable experience.",
    "Don't miss out on this unique opportunity.", "Come and learn with us.",
    "We hope to see you there.", "Witness the future of knowledge", "Celebrate the spirit of learning",
    "Experience the magic of knowledge", "Explore the wonders of history", "Connect with the community!"
]


positive_review_beginnings = [
    "A fascinating exploration of", "An amazing collection of", "The event was well-organized and",
    "The organizer did a fantastic job with", "I found the event to be very", "A must-see for anyone interested in",
    "The activities were simply", "I loved the interactive elements and", "It was an educational experience with",
    "The event showcased a brilliant", "The presenters were engaging and informative on", "The discussions were lively and insightful on",
    "The exhibits were well-curated and engaging on", "The venue provided a perfect setting for", "The content covered was diverse and comprehensive on",
    "The event exceeded my expectations on", "The sessions were thought-provoking and enlightening on", "The workshops were hands-on and practical on",
    "The event atmosphere was vibrant and inspiring on", "The event presentations were top-notch on"
]

positive_review_middles = [
    "topics and beyond.", "speakers and sessions.", "informative displays.", "the theme and presentation.",
    "inspiring and insightful.", "history and culture.", "stunning and captivating.", "engaging exhibits.",
    "informative guides.", "collection of works.", "new insights and discoveries.", "the latest innovations and developments.",
    "interactive experiences and demonstrations.", "exciting activities and performances.", "diverse perspectives and ideas.",
    "cutting-edge research and findings.", "memorable moments and experiences.", "the unique ambiance and setting.",
    "thought-provoking discussions and debates."
]

positive_review_endings = [
    "Highly recommend!", "A bit crowded, but worth it.", "An unforgettable experience.", "A delightful experience.",
    "Very educational and fun.", "Will visit again!", "Perfect for families.", "A real eye-opener.", "Not to be missed.",
    "Thoroughly enjoyed it.", "Absolutely fantastic!", "A must-attend event!", "Truly inspiring!", "Definitely worth the visit!",
    "An enriching experience!", "Impressive and enjoyable!", "Exceptional in every way!", "Well worth the time!", "Remarkable and memorable!"
]


negative_review_beginnings = [
    "A disappointing collection of", "The event was poorly organized and", "The organizer did a terrible job with",
    "I found the event to be very", "Not a great display of", "The activities were", "The interactive displays were",
    "It was a boring experience with", "The event had a lackluster", "The presenters were uninspiring and lacked clarity on",
    "The discussions were dull and unengaging on", "The exhibits were disappointing and lacked depth on",
    "The venue was not suitable for", "The content covered was confusing and poorly presented on",
    "The event did not live up to expectations on", "The sessions were unstructured and unclear on",
    "The workshops were poorly executed and uninformative on", "The event atmosphere was dull and uninviting on",
    "The event presentations were below standard on"
]


negative_review_middles = [
    "topics and beyond.", "speakers and sessions.", "informative displays.", "the theme and presentation.",
    "uninspiring and dull.", "history and culture.", "mediocre and uninteresting.", "engaging exhibits.",
    "informative guides.", "collection of works.", "new insights and discoveries.", "the latest innovations and developments.",
    "interactive experiences and demonstrations.", "exciting activities and performances.", "diverse perspectives and ideas.",
    "cutting-edge research and findings.", "memorable moments and experiences.", "the unique ambiance and setting.",
    "thought-provoking discussions and debates."
]


negative_review_endings = [
    "Not recommended.", "A waste of time.", "Forgettable experience.", "Very disappointing.", "Won't visit again.",
    "Not worth the visit.", "A letdown for families.", "Not an eye-opener.", "Definitely to be missed.",
    "Didn't enjoy it.", "Not impressive at all.", "Could be improved greatly.", "Lacked substance and depth.", "Poorly executed.",
    "Would not recommend.", "Disappointing and underwhelming.", "Not up to par.", "Uninspired and unremarkable.", "Quite dissatisfied."
]


def generate_event():
    start_date_time = fake.date_between(start_date='-5y', end_date='+1y')
    today = datetime.now().date()

    if today < start_date_time:
        reviews = []
    else:
        reviews = [generate_review() for _ in range(random.randint(1, 10))]

    duration_minutes = random.randint(2, 7) * 30
    price = random.randint(0, 25)
    event_id = generate_random_id()
    event_name = generate_event_name()
    reviews = reviews

    event = {
        "id": event_id,
        "name": event_name,
        "description": generate_description(event_name),
        "startDateTime": start_date_time.isoformat(),
        "durationMinutes": duration_minutes,
        "price": price,
        "organizer": generate_organizer(),
        "room": generate_room(),
        "reviews": reviews
    }

    return event

def generate_random_id(length=20):
    characters = string.ascii_letters + string.digits
    return ''.join(random.choice(characters) for _ in range(length))

def generate_event_name():
    name = random.choice(adjectives) + " " + random.choice(nouns)
    if random.random() > 0.5:
        name += " " + random.choice(phrases)
    return name

def generate_description(event_name):
    introduction = f"{random.choice(description_introductions)} {event_name}."
    background = random.choice(description_backgrounds)
    experience = ""
    if random.random() > 0.5:
        experience = random.choice(description_experiences)

    conclusion = random.choice(description_conclusions)

    description = f"{introduction} {background} {experience} {conclusion}"

    return description



def generate_room():
    floor = random.randint(1, 5)
    room_base_number = floor * 10 + random.randint(0,9)
    room_number = str(room_base_number)
    if random.choice([True, False]):
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

def generate_events(num):
    return [generate_event() for _ in range(num)]

def save_to_excel(data, filename):
    wb = Workbook()
    ws = wb.active

    header = [
        'id', 'name', 'description', 'startDate', 'duration',
        'price', 'organizer_firstName', 'organizer_lastName',
        'room_floor', 'room_number', 'reviews'
    ]

    ws.append(header)

    for event in data:
        reviews = [
            f"Reviewer: {review['reviewer']}, Rating: {review['rating']}, Text: {review['text']}"
            for review in event['reviews']
        ]

        row = [
            event['id'],
            event['name'],
            event['description'],
            event['startDateTime'],
            event['durationMinutes'],
            event['price'],
            event['organizer']['firstName'],
            event['organizer']['lastName'],
            event['room']['floor'],
            event['room']['number'],
            ' | '.join(reviews)
        ]
        ws.append(row)

    wb.save(filename)

def index_events_to_elasticsearch(events):
    actions = []
    for event in events:
        action = {
            "_index": "event",  # Elasticsearch index name
            "_source": {
                "name": event['name'],
                "description": event['description'],
                "startDateTime": event['startDateTime'],
                "durationMinutes": event['durationMinutes'],
                "price": event['price'],
                "organizer": event['organizer'],
                "room": event['room'],
                "reviews": event['reviews']
            }
        }
        actions.append(action)

    # Use Elasticsearch bulk helper to efficiently index multiple documents
    success, _ = bulk(es, actions)
    print(f"Indexed {success} documents")

if __name__ == "__main__":
    num_events = 1000
    events = generate_events(num_events)
    save_to_excel(events, 'events.xlsx')
    index_events_to_elasticsearch(events)
    if es.indices.exists(index="event"):
        print("Index 'event' already exists.")

