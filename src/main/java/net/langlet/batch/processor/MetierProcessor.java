// src/main/java/net/langlet/batch/processor/MetierProcessor.java

package net.langlet.batch.processor;

import net.langlet.batch.model.Societe;
import net.langlet.batch.model.SocieteComplete;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

@Component
public class MetierProcessor implements ItemProcessor<SocieteComplete, SocieteComplete> {
    @Override
    public SocieteComplete process(SocieteComplete societeComplete) {
        // Faire un traitement métier avec les SocieteComplete...
        return societeComplete;
    }
}
