package com.example.demo;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryStore {
    public static final Map<String, List<Chore>> CHORES_BY_CHILD = new ConcurrentHashMap<>();
    public static final Map<String, Integer>     COINS_BY_CHILD   = new ConcurrentHashMap<>();
}
