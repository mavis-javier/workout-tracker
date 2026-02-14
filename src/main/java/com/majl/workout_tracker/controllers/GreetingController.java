package com.majl.workout_tracker.controllers;

import com.majl.workout_tracker.entities.Greeting;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {
    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    /*
    What is AtomicLong and why is it used instead of Long?
    * Atomic Operations: Standard incrementing (count++) is not atomic; it involves three separate steps (read, update, write). If two threads increment a standard Long at the same time, they might both read the same value and write back the same incremented result, leading to a "lost update".
    * Lock-Free Performance: Unlike using the synchronized keyword, which blocks other threads, AtomicLong uses low-level CPU instructions called Compare-And-Swap (CAS). This makes it significantly faster for high-concurrency counters.
    * Thread Visibility: It ensures that when one thread updates the value, the change is immediately visible to all other threads.
    * Convenience: Methods like incrementAndGet() perform the increment and return the new value in a single, thread-safe step.
    * */

    @GetMapping("/greeting")
    public Greeting greeting(@RequestParam(defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @RequestMapping("/resource")
    public Map<String, Object> home() {
        Map<String, Object> model = new HashMap<>();
        model.put("id", UUID.randomUUID().toString()); // universally unique identifier
        model.put("content", "Hello World");
        return model;
    }
}
