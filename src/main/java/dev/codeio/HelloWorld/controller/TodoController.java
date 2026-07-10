package dev.codeio.HelloWorld.controller;

import dev.codeio.HelloWorld.models.Todo;
import dev.codeio.HelloWorld.service.TodoService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
@Slf4j
public class TodoController {

    private final TodoService todoService;

    @RestController
    public class HomeController {

        @GetMapping("/")
        public String home() {
            return "Todo API Running";
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Todo>> getTodos() {

        log.info("Fetching all todos");

        return ResponseEntity.ok(
                todoService.getTodos()
        );
    }

    @GetMapping("/page")
    public ResponseEntity<Page<Todo>> getTodosPaged(
            @RequestParam int page,
            @RequestParam int size) {

        log.info(
                "Fetching todos page {} size {}",
                page,
                size
        );

        return ResponseEntity.ok(
                todoService.getAllTodosPages(
                        page,
                        size
                )
        );
    }

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Todo retrieved successfully"
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "Todo not found"
            )
    })
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                todoService.getTodoById(id)
        );
    }

     @PostMapping("/create")
public ResponseEntity<Todo> createTodo(
        @RequestBody Todo todo) {

    System.out.println("Received Title: " + todo.getTitle());
    System.out.println("Received Description: " + todo.getDescription());
    System.out.println("Received Completed: " + todo.getIsCompleted());

    Todo createdTodo = todoService.createTodo(todo);

    System.out.println("Saved Todo: " + createdTodo);

    return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdTodo);
}

    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(
            @PathVariable Long id,
            @RequestBody Todo todo) {

        return ResponseEntity.ok(
                todoService.updateTodo(
                        id,
                        todo
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTodo(
            @PathVariable Long id) {

        todoService.deleteTodo(id);

        return ResponseEntity.ok(
                "Todo deleted successfully"
        );
    }
}