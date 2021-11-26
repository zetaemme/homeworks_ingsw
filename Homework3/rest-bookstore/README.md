# Bookstore

This a quick and easy implementation of a bookstore REST API using Spring Boot.

## Operations
- `GET /books`: to retrieve a list of all stored books.
- `GET /book/{id}`: to retrieve the book with the given ID.
- `POST /book?title={title}&author={author}&price={price}`: to add a new book to the bookstore.
- `PUT /book`: to update a book. New data must be provided in JSON format in the body of the request.
- `DELETE /book?id={id}`: to delete the book with the given ID.