package com.reactivespring.reactivespring.fluxandmonoplayground;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;

class ColdAndHotPublisherTest {

    @Test
    void coldPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        stringFlux.subscribe(s -> System.out.println("subscriber 1: " + s));    //emits from beginning
        Thread.sleep(2000);

        stringFlux.subscribe(s -> System.out.println("subscriber 2: " + s));    //emits from beginning
        Thread.sleep(4000);
    }

    @Test
    void hotPublisherTest() throws InterruptedException {
        Flux<String> stringFlux = Flux.just("A", "B", "C", "D", "E", "F")
                .delayElements(Duration.ofSeconds(1));

        ConnectableFlux<String> connectableFlux = stringFlux.publish();
        connectableFlux.connect();

        connectableFlux.subscribe(s -> System.out.println("subscriber 1: " + s));
        Thread.sleep(3000);

        connectableFlux.subscribe(s -> System.out.println("subscriber 2: " + s)); // does not emit from beginning
        Thread.sleep(4000);
    }
}
