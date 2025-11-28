#!/usr/bin/env python3
import random
import json

data = {
    "temperature": round(random.uniform(15.0, 30.0), 1),
    "humidity": round(random.uniform(30.0, 80.0), 1)
}
print(json.dumps(data))