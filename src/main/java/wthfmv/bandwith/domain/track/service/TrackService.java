package wthfmv.bandwith.domain.track.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wthfmv.bandwith.domain.track.dto.req.TrackPostReq;
import wthfmv.bandwith.domain.track.dto.res.TrackRes;
import wthfmv.bandwith.domain.track.entity.Track;
import wthfmv.bandwith.domain.track.repository.TrackRepository;
import wthfmv.bandwith.domain.websocket.message.WebSocketMessageMethod;
import wthfmv.bandwith.domain.websocket.message.WebsocketMessage;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TrackService {
    private final TrackRepository trackRepository;
    private final MongoTemplate mongoTemplate;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void postTrack(TrackPostReq trackPostReq) {
        trackRepository.save(new Track(trackPostReq));
    }

    @Transactional
    public TrackRes getTrack(String trackId){
        Track track = trackRepository.findById(trackId).orElseThrow(
                () -> new RuntimeException("해당 트랙 없음")
        );

        if(Boolean.TRUE.equals(redisTemplate.hasKey(trackId))){
            String uuid = (String) redisTemplate.opsForValue().get(trackId);
            redisTemplate.expire(trackId, 30, TimeUnit.MINUTES);
            return new TrackRes(track, uuid);
        } else {
            String uuid = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(trackId, uuid, 30, TimeUnit.MINUTES);
            return new TrackRes(track, uuid);
        }
    }
    @Transactional
    public String updateAndGetTrack(WebsocketMessage websocketMessage) {

        //트랙아이디로 찾기 uuid 찾기
        String redisUUID = (String) redisTemplate.opsForValue().get(websocketMessage.getTrackId());
        String newUUID = UUID.randomUUID().toString();
        redisTemplate.opsForValue().set(websocketMessage.getTrackId(), newUUID , 30, TimeUnit.MINUTES);

        if(!Objects.equals(redisUUID, websocketMessage.getUuid())){
            throw new RuntimeException("갱신 필요");
        }

        Query query = new Query(Criteria.where("_id").is(websocketMessage.getTrackId()));
        for(int i = 0; i < websocketMessage.getValue().length; i++){
            switch (websocketMessage.getWebSocketMessageMethod()[i]){
                case DELETE -> {
                    Update update = new Update();
                    update.unset(websocketMessage.getQuery()[i]);
                    mongoTemplate.updateFirst(query, update, Track.class);
                }
                case UPDATE, CREATE -> {
                    Update update = new Update();
                    update.set(websocketMessage.getQuery()[i], websocketMessage.getValue()[i]);
                    mongoTemplate.updateFirst(query, update, Track.class);
                }
            }

        }

        return newUUID;
    }
}
