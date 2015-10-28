package com.tripcaddie.backend.community.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tripcaddie.backend.community.model.AbuseReasons;
import com.tripcaddie.backend.community.model.GameComment;
import com.tripcaddie.backend.community.model.GameRating;
import com.tripcaddie.backend.community.model.Games;
import com.tripcaddie.backend.community.model.JokeComment;
import com.tripcaddie.backend.community.model.JokeRating;
import com.tripcaddie.backend.community.model.Jokes;

@Transactional
@Service("communityDao")
public class CommunityDaoImpl implements CommunityDao {

	

	@Resource(name = "hibernateTemplate")
	private HibernateTemplate hibernateTemplate;

	@Override
	public List<Games> getGamesbyDate() {
		ArrayList<Games> gamesUpdated=new ArrayList<Games>();
	ArrayList<Games> games= (ArrayList<Games>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Games.class)
				.addOrder(Order.desc("createdDate"))
				.add(Restrictions.eq("abuse", 0))
				.list();
	updatingGames(gamesUpdated, games);
	return gamesUpdated;
	}

	private void updatingGames(ArrayList<Games> gamesUpdated,
			ArrayList<Games> games) {

		for (Games games2 : games) {
				
				List<GameRating> gameRatings=getGameAvgRating(games2.getGameId());
				double avg=0;
				for(GameRating gameRating:gameRatings){
					avg=avg+gameRating.getRating();
				}
				
				//update avg rating in DB
				if(gameRatings!=null && !gameRatings.isEmpty()){
					games2.setAvgRating((avg/gameRatings.size()));
				}
				else{
					games2.setAvgRating((double) 0);
				}
				
				updateEntity(games2);
				gamesUpdated.add(games2);
		}
		
	}

	@Override
	public List<Games> getGamesbyRating() {
		ArrayList<Games> gamesUpdated=new ArrayList<Games>();
		ArrayList<Games> games=(ArrayList<Games>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Games.class)
					.addOrder(Order.desc("avgRating"))
					.add(Restrictions.eq("abuse", 0))
					.list();
		updatingGames(gamesUpdated, games);
		return gamesUpdated;
	}

	@Override
	public Games getGameById(int gameId) {
		Games games=hibernateTemplate.load(Games.class,gameId);
		
		List<GameRating> gameRatings=getGameAvgRating(games.getGameId());
		double avg=0;
		for(GameRating gameRating:gameRatings){
			avg=avg+gameRating.getRating();
		}
		
		//update avg rating in DB
		if(gameRatings!=null && !gameRatings.isEmpty()){
			games.setAvgRating((avg/gameRatings.size()));
		}
		else{
			games.setAvgRating((double) 0);
		}
		
		updateEntity(games);
		return games;
	}

	@Override
	public List<Jokes> getJokessbyDate() {
		ArrayList<Jokes> jokesUpdated=new ArrayList<Jokes>();
	ArrayList<Jokes> jokes=(ArrayList<Jokes>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Jokes.class)
					.addOrder(Order.desc("createdDate"))
					.add(Restrictions.eq("abuse", 0))
					.list();
	updatingJokes(jokesUpdated, jokes);
	return jokesUpdated;
	}

	@Override
	public List<Jokes> getJokesbyRating() {
		ArrayList<Jokes> jokesUpdated=new ArrayList<Jokes>();
		ArrayList<Jokes> jokes=(ArrayList<Jokes>)hibernateTemplate.getSessionFactory().getCurrentSession().createCriteria(Jokes.class)
					.addOrder(Order.desc("avgRating"))
					.add(Restrictions.eq("abuse", 0))
					.list();
		updatingJokes(jokesUpdated, jokes);
		return jokesUpdated;
	}

	@Override
	public Jokes getJokeById(int jokeId) {
		Jokes jokes= hibernateTemplate.load(Jokes.class,jokeId);
		List<JokeRating> jokeRatings=getJokeAvgRating(jokes.getJokeId());
		double avg=0;
		for(JokeRating jokeRating:jokeRatings){
			avg=avg+jokeRating.getRating();
		}
		
		//update avg rating in DB
		if(jokeRatings!=null && !jokeRatings.isEmpty()){
			jokes.setAvgRating((avg/jokeRatings.size()));
		}
		else{
			jokes.setAvgRating((double) 0);
		}
		
		updateEntity(jokes);
		return jokes;
	}

	@Override
	public GameComment getGameCommentbyId(int commentId) {
		return hibernateTemplate.load(GameComment.class,commentId);
	}

	@Override
	public void saveEntity(Object entity) {
		hibernateTemplate.save(entity);
		
	}

	@Override
	public void updateEntity(Object entity) {
		hibernateTemplate.update(entity);
		
	}

	@Override
	public void deleteEntity(Object entity) {
		hibernateTemplate.delete(entity);
		
	}

	@Override
	public JokeComment getJokeCommentbyId(int commentId) {
		return hibernateTemplate.load(JokeComment.class,commentId);
	}

	@Override
	public GameRating getGameRating(int gameId, int userId) {
		List<GameRating> gameRatings=hibernateTemplate.find("from GameRating where gameRatingPK.game.gameId="+gameId+" and gameRatingPK.user.userId="+userId);
		if(gameRatings!=null && !gameRatings.isEmpty()){
			return gameRatings.get(0);
		}
		return null;
	}

	@Override
	public JokeRating getJokeRating(int jokeId, int userId) {
		List<JokeRating> jokeRatings=hibernateTemplate.find("from JokeRating where jokeRatingPK.jokes.jokeId="+jokeId+" and jokeRatingPK.user.userId="+userId);
		if(jokeRatings!=null && !jokeRatings.isEmpty()){
			return jokeRatings.get(0);
		}
		return null;
	}

	@Override
	public List<GameComment> getGameComments(int gameId) {
		return (ArrayList<GameComment>) hibernateTemplate.find("from GameComment where games.gameId="+gameId+" ORDER BY lastUpdatedDate DESC");
	}

	@Override
	public List<JokeComment> getJokeComments(int jokeId) {
		return (ArrayList<JokeComment>) hibernateTemplate.find("from JokeComment where jokes.jokeId="+jokeId+" ORDER BY lastUpdatedDate DESC");
	}

	@Override
	public List<GameRating> getGameAvgRating(int gameId) {
		List<GameRating> gameRatings=hibernateTemplate.find("from GameRating where gameRatingPK.game.gameId="+gameId);
		return gameRatings;
	}

	@Override
	public List<JokeRating> getJokeAvgRating(int jokeId) {
		List<JokeRating> jokeRatings=hibernateTemplate.find("from JokeRating where jokeRatingPK.jokes.jokeId="+jokeId);
		return jokeRatings;
	}

	private void updatingJokes(ArrayList<Jokes> jokesUpdated,
			ArrayList<Jokes> jokes) {

		for (Jokes jokes2 : jokes) {
				
				List<JokeRating> jokeRatings=getJokeAvgRating(jokes2.getJokeId());
				double avg=0;
				for(JokeRating jokeRating:jokeRatings){
					avg=avg+jokeRating.getRating();
				}
				
				//update avg rating in DB
				if(jokeRatings!=null && !jokeRatings.isEmpty()){
					jokes2.setAvgRating((avg/jokeRatings.size()));
				}
				else{
					jokes2.setAvgRating((double) 0);
				}
				
				updateEntity(jokes2);
				jokesUpdated.add(jokes2);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<AbuseReasons> getAbuseReasons() {
	
		return (List<AbuseReasons>)hibernateTemplate.find("from AbuseReasons ");
	}

	@Override
	public AbuseReasons getAbuseReason(int reasonId) {
		
		return hibernateTemplate.load(AbuseReasons.class, reasonId);
	}
}
