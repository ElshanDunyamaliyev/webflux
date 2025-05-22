🧠 Understanding the Event Loop in Spring WebFlux

This document provides a simplified explanation of how non-blocking I/O and the event loop model work in Spring WebFlux, especially when used with a reactive web client.


⚙️ The Core Concept

In a non-blocking/reactive model like Spring WebFlux, the system doesn’t create a new thread for every request. Instead, it uses a small number of threads (typically one per CPU core) to handle many tasks asynchronously.

🧵 Thread & Task Queue Architecture

Internally, the event loop works with two main concepts:

Task Queue (Outbound Queue):
This queue stores outgoing requests that the client needs to send to external services.

Worker Thread (1 per CPU core):
Each thread constantly monitors the queue:

If there are tasks, it picks them up and sends requests.

If there are no tasks, it simply waits (idle).

📤 Sending Requests: Outbound Queue

Let’s say your application needs to fetch product information for multiple product IDs (e.g., 123, 124, 125...).

When you send these requests (e.g., in a for loop), each task is added to the outbound queue. The thread:

Picks up productId: 123 and sends the request.

Instead of waiting for the response (which may take ~1 second), it immediately picks up the next task (productId: 124) and sends it.

This continues for subsequent requests.

🔁 Key idea: The thread does not block waiting for a response. It keeps sending new requests and stays busy.

📥 Receiving Responses: Inbound Queue

While the thread is sending new requests, the responses from external services (like product services) may start arriving.

At this point:

The Operating System (OS) notifies the application when a response is ready.

The response is then placed in another queue called the inbound queue.

This queue is processed asynchronously — responses are handed over to the appropriate handlers/callbacks.

📌 Important Note: Responses may arrive out of order.

For example, even though you requested productId: 123 first and productId: 124 next, the response for 124 might arrive before 123. This is normal in asynchronous network communication.

✅ Why This Is Efficient
With this architecture:

You don’t need hundreds of threads to handle high concurrency.

A small, fixed number of threads (e.g., one per CPU core) can send and handle hundreds or thousands of concurrent requests.

This is the foundation of reactive programming and non-blocking I/O in frameworks like Spring WebFlux.

🧩 Summary

Spring WebFlux uses an event loop model to handle I/O efficiently.

Threads don’t block while waiting for responses.

Tasks are managed via outbound and inbound queues.

This allows scalable, high-performance applications with fewer system resources.

// Sending 100 request example

    @GetMapping(value = "/get-100-products")
    public void get100Products() throws InterruptedException {
        for (int i = 1; i < 100; i++) {
            orderClient.get()
                    .uri("/lec01/product/{id}", i)
                    .retrieve()
                    .bodyToMono(Product.class)
                    .doOnNext(product -> log.info("{}",product))
                    .subscribe();
        }
        Thread.sleep(Duration.ofSeconds(2));
    }