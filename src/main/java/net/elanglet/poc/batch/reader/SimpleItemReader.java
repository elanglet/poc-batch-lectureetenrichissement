package net.elanglet.poc.batch.reader;

import net.elanglet.poc.batch.model.Item;
import net.elanglet.poc.batch.processor.BusinessProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Collectors;

@Component
public class SimpleItemReader implements ItemReader<Item> {

    private static final Logger logger = LoggerFactory.getLogger(SimpleItemReader.class);

    private final Iterator<Item> iterator;

    public SimpleItemReader() {
        List<Item> items = LongStream.rangeClosed(1, 23)
                .mapToObj(i -> new Item(i, "payload-" + i))
                .collect(Collectors.toList());
        this.iterator = items.iterator();
    }

    @Override
    public Item read() {
        if (iterator.hasNext()) {
            Item item = iterator.next();
            logger.info("Processing next item id={}", item.id());
            return item;
        }
        return null;
    }
}
