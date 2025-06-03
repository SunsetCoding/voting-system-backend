INSERT INTO polls (id, question) VALUES (1, '你最喜欢的编程语言是什么？');

INSERT INTO options (id, text, vote_count, poll_id) VALUES
                                                        (1, 'Java', 0, 1),
                                                        (2, 'Python', 0, 1),
                                                        (3, 'JavaScript', 0, 1),
                                                        (4, 'Go', 0, 1),
                                                        (5, 'Rust', 0, 1);