# crossword

To prepare this as an eclipse project: gradle build eclipse

A program for creating 'American Crosswords' like what you would see in NYTimes.

You supply a word list in JSON which is an array of word objects and word group objects. The wordlist format is demonstrated below. Word objects define a word to place in the crossword and a score to reward the alogirthm for successfully placing the word in the crossword. Word groups contain words, and allow word usage to be further qualified.

[
  {
    "min": 0,
    "max": 1,
    "words":[
      {
        "word":"elephant",
        "score":50
      },
      {
        "word":"mammoth",
        "score":10
      }
    ]
  },
  {
    "only": [0,2],
    "words":[
      {
        "word":"power",
        "score":100
      },
      {
        "word":"ranger",
        "score":100
      }
    ]
  },
  {
    "word":"ninja",
    "score":1
  },
  {
    "word":"samurai",
    "score":5
  },
  {
    "word":"monk",
    "score":7
  }
]