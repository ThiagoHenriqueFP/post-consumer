
# Posts consumption

This project is a challenge from internship program Compass.uol
This server will receive a request to search a post from https://jsonplaceholder.typicode.com/posts and process the response, adding actions history, like created, post_find, comment_find, failed etc.


## API Docs

#### Get all processed posts

```http
  GET /posts/
```

| Parameter   | Type       | Description                           |
| :---------- | :--------- | :---------------------------------- |
| `size` | `string` | **Optional** change page size|
| `pageNumber` | `string` | **Optional** change page|
| `direction` | `string` | **Optional** change order [asc, desc]|
| `sortBy` | `string` | **Optional** change filter to sort|

#### Process a post

```http
  POST /posts/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Integer` | **Mandatory**. post id|


#### Disable a post

```http
  DELETE /posts/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Integer` | **Mandatory**. post id|

#### Reprocess a post

```http
  PUT /posts/${id}
```

| Parâmetro   | Tipo       | Descrição                                   |
| :---------- | :--------- | :------------------------------------------ |
| `id`      | `Integer` | **Mandatory**. post id|

## Event Order

- CREATED: Initial state of a new post.
- POST_FIND: Indicates that the app is searching for basic post data.
- POST_OK: Indicates that the basic post data is already available.
- COMMENTS_FIND: Indicates that the app is searching for post comments.
- COMMENTS_OK: Indicates that the post comments are already available.
- ENABLED: Indicates that the post has been successfully processed and is enabled.
- DISABLED: Indicates that the post is disabled, either due to a processing failure or by user decision.
- UPDATING: Indicates that the post needs to be reprocessed.
- FAILED: Indicates a processing error.

