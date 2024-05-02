package main

import (
	"bufio"
	"fmt"
	"os"
	"sort"
	"strings"
)

// Generate one-letter variants of a given word
func generateOneLetterVariants(word string, validWords map[string]bool) []string {
	variants := []string{}
	wordRunes := []rune(word)

	for i := 0; i < len(wordRunes); i++ {
		originalChar := wordRunes[i]
		for c := 'a'; c <= 'z'; c++ {
			if c != originalChar {
				wordRunes[i] = c
				newWord := string(wordRunes)
				if validWords[newWord] {
					variants = append(variants, newWord)
				}
			}
		}
		wordRunes[i] = originalChar
	}

	// Sort variants in lexicographical order
	sort.Strings(variants)

	return variants
}

func main() {
	// Open the file containing the words
	file, err := os.Open("words_alpha.txt")
	if err != nil {
		fmt.Println("Error opening file:", err)
		return
	}
	defer file.Close()

	// Load words into a set for quick lookup
	validWords := make(map[string]bool)
	scanner := bufio.NewScanner(file)
	for scanner.Scan() {
		word := strings.TrimSpace(scanner.Text())
		if len(word) > 0 {
			validWords[word] = true
		}
	}

	// Check for scan errors
	if err := scanner.Err(); err != nil {
		fmt.Println("Error reading file:", err)
		return
	}

	// Create the key-value mapping
	wordMap := make(map[string][]string)
	for word := range validWords {
		variants := generateOneLetterVariants(word, validWords)
		if len(variants) > 0 {
			wordMap[word] = variants
		}
	}

	// Sort the keys of the map
	keys := make([]string, 0, len(wordMap))
	for key := range wordMap {
		keys = append(keys, key)
	}
	sort.Strings(keys)

	// Save the key-value pairs to a text file
	outputFile, err := os.Create("output.txt")
	if err != err {
		fmt.Println("Error creating output file:", err)
		return
	}
	defer outputFile.Close()

	writer := bufio.NewWriter(outputFile)

	for _, key := range keys { // Iterate over sorted keys
		// Format the key-value pair
		formattedValues := strings.Join(wordMap[key], ", ")
		line := fmt.Sprintf("%s: %s\n", key, formattedValues)

		if _, err := writer.WriteString(line); err != err {
			fmt.Println("Error writing to file:", err)
			return
		}
	}

	// Flush the buffer
	if err := writer.Flush(); err != err {
		fmt.Println("Error flushing buffer:", err)
		return
	}

	fmt.Println("Key-value mapping saved to 'output.txt'")
}
