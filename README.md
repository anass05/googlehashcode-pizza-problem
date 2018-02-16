# Google hash code pizza problem

This is our solution to the google hash code [pizza problem](https://hashcodejudge.withgoogle.com/download/blob/AMIfv94WXlVJBvEkhPOsWc1jLtD2abO7MXgXStc0oHAPH2l0JirdzDFPtke__rF2_IHMcUz_AiPYDmI5RUEh9113M0saTPd8QpOcG4rx4jvB8b7fqXQp4CKSLtZtCApX4GSjj7AhxyRFCJ1s458GxfLvRyOW3LanFg5kuYr7pMhXZpPkRqbSvqBdJK-1axntl-ySUdDGe9eTjYgkvCh_wUAWEwdMcNmcUwmD79M56zZ6zR16Dv9Yzf_MUU2MfbN1dSD7JtuSB1ZlNBFOH-jhVI2GsXrlg7jzMAfcTDtZ8-Xg6UDMKO89VP1nrvwoPKLHFU3urg4AK-EhK02WGMVtARqbQH84acNLhvHaoi1Cp3B37PRYcosWRaWV0DgqmPHUOaBaH4IkGcitn_RA0kKenYhOUMpaepbIzYwILZE7HS5lZvW8ZpC-yYc), written in java

## Total score 906,770

The total score we achieved using this algorithm was 906,770 

**Example**: 15 points

**Small**: 35 points

**Medium**: 43 749 points

**Big**: 862 971 points


## How does it work?

### Cutting the pizza

The first step is getting all possible slices and putting them in a main list while satisfying the three constraints.

### Prioritizing the slices

The second step is prioritizing the slices, which means giving each slice a priority number according to how much mushrooms and tomatoes it has and how big it is.

The slices with the biggest size and least number of ingredients have the highest priority number.

### Sorting the slices

The slices are sorted from highest priority to the lowest one.

### Dividing the pizza

In order to make the program run faster we virtually divided the pizza into five sections.

**TL**: Top left section.

**TR**: Top right section.

**BL**: bottom left section.

**BR**: bottom right section.

**IN**: intersection section.

### Indexing the slices

Each slice received a **TAG**to where it belongs in the pizza *TL* or *TR* or *BL* or *BR* or *IN*.

### Getting the results

The last step is creating five lists, each list is responsible for storing the slices that belong to one of the five sections.

Each slice of the main list is being tested with all the slices in it's section's list, if it overlaps with any of them then we move on to the next one, else we add it to the list.

What makes this algorithm strong and fast is the priority order and the pizza sectioning.

To run the program with the input file example.in open the file Main.java and change the line:
```
ops.readFile("example.in");
```

## Authors

* [SADIK Anass](https://github.com/anass05)

* [MOUBARAKI Amine](https://github.com/MOUBARAKI)
