
# Posts consumption

This project is a challenge from internship program Compass.uol
This server will receive a request to search a post from https://jsonplaceholder.typicode.com/posts and process the response, adding actions history, like created, post_find, comment_find, failed etc.


## Flow

After request on ```POST /posts/${id}``` an internal flow will be started, fetching the post and assiging histories to him and at end, will be returned the post processed

Inside resource folder, has an script to fire 100 curl request to this api and measure the total time to fetch all posts


## API Docs

#### Get all processed posts

```http
  GET /posts/
```

| Parameter   | Type       | Description                           |
| :---------- | :--------- | :---------------------------------- |
| `pageSize` | `string` | **Optional** change page size|
| `pageNumber` | `string` | **Optional** change page|
| `direction` | `string` | **Optional** change order [asc, desc]|

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

