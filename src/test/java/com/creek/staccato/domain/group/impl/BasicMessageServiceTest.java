package com.creek.staccato.domain.group.impl;

import org.junit.Before;
import org.junit.Test;

import com.creek.staccato.AbstractRepositoryTest;
import com.creek.staccato.domain.message.MessageService;
import com.creek.staccato.domain.message.impl.MessageServiceImpl;
import com.creek.staccato.repository.emulator.EmulatorMessageRepository;

/**
 * 
 * @author Andrey Pereverzin
 *
 */
public class BasicMessageServiceTest extends AbstractRepositoryTest {
    private MessageService messageService;
    
    @Before
    public void setUp() {
        messageService = new MessageServiceImpl();
        messageService.setMessageRepository(new EmulatorMessageRepository());
    }
    
    @Test
    public void test() {
        //
    }
}
