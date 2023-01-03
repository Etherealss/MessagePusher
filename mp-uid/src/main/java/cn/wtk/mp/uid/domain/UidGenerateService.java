package cn.wtk.mp.uid.domain;

import cn.wtk.mp.uid.infrastructure.generator.IUidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wtk
 * @date 2023-01-02
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class UidGenerateService {
    private final IUidGenerator uidGenerator;

    public List<Long> allocUids(AllocUidCommand command) {
        List<Long> ids = new ArrayList<>(command.getCount());
        for (int i = 0; i < command.getCount(); i++) {
            ids.add(uidGenerator.nextId());
        }
        return ids;
    }

    public long allocUid() {
        return uidGenerator.nextId();
    }
}
